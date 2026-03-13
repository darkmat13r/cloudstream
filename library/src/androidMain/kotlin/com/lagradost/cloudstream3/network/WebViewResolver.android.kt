package com.lagradost.cloudstream3.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.webkit.*
import com.lagradost.api.Log
import com.lagradost.api.getContext
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.mvvm.debugException
import com.lagradost.cloudstream3.mvvm.logError
import com.lagradost.cloudstream3.mvvm.safe
import com.lagradost.cloudstream3.utils.Coroutines.main
import com.lagradost.cloudstream3.utils.Coroutines.mainWork
import com.lagradost.cloudstream3.utils.Coroutines.threadSafeListOf
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.net.URI

/**
 * When used as Interceptor additionalUrls cannot be returned, use WebViewResolver(...).resolveUsingWebView(...)
 * @param interceptUrl will stop the WebView when reaching this url.
 * @param additionalUrls this will make resolveUsingWebView also return all other requests matching the list of Regex.
 * @param userAgent if null then will use the default user agent
 * @param useOkhttp will try to use the okhttp client as much as possible, but this might cause some requests to fail. Disable for cloudflare.
 * @param script pass custom js to execute
 * @param scriptCallback will be called with the result from custom js
 * @param timeout close webview after timeout
 * */
actual class WebViewResolver actual constructor(
    val interceptUrl: Regex,
    val additionalUrls: List<Regex>,
    val userAgent: String?,
    val useOkhttp: Boolean,
    val script: String?,
    val scriptCallback: ((String) -> Unit)?,
    val timeout: Long
) : Interceptor {

    actual companion object {
        actual var webViewUserAgent: String? = null
        actual val DEFAULT_TIMEOUT = 60_000L
        private const val TAG = "WebViewResolver"

        @JvmName("getWebViewUserAgent1")
        fun getWebViewUserAgent(): String? {
            return webViewUserAgent ?: (getContext() as? Context)?.let { ctx ->
                runBlocking {
                    mainWork {
                        WebView(ctx).settings.userAgentString.also { userAgent ->
                            webViewUserAgent = userAgent
                        }
                    }
                }
            }
        }
    }

    override suspend fun intercept(request: HttpRequestBuilder) {
        val url = request.url.toString()
        val fixedRequest = resolveUsingWebView(url).first
        if (fixedRequest != null) {
            request.url(fixedRequest.url)
            fixedRequest.headers.forEach { (key, value) ->
                request.header(key, value)
            }
        }
    }

    actual suspend fun resolveUsingWebView(
        url: String,
        referer: String?,
        method: String,
        requestCallBack: (WebViewRequest) -> Boolean,
    ): Pair<WebViewRequest?, List<WebViewRequest>> =
        resolveUsingWebView(url, referer, emptyMap(), method, requestCallBack)

    actual suspend fun resolveUsingWebView(
        url: String,
        referer: String?,
        headers: Map<String, String>,
        method: String,
        requestCallBack: (WebViewRequest) -> Boolean,
    ): Pair<WebViewRequest?, List<WebViewRequest>> {
        return try {
            val request = WebViewRequest(
                url = url,
                headers = headers + (if (referer != null) mapOf("Referer" to referer) else emptyMap()),
                method = method
            )
            resolveUsingWebView(request, requestCallBack)
        } catch (e: java.lang.IllegalArgumentException) {
            logError(e)
            debugException { "ILLEGAL URL IN resolveUsingWebView!" }
            return null to emptyList()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    actual suspend fun resolveUsingWebView(
        request: WebViewRequest,
        requestCallBack: (WebViewRequest) -> Boolean
    ): Pair<WebViewRequest?, List<WebViewRequest>> {
        val url = request.url
        val headers = request.headers
        Log.i(TAG, "Initial web-view request: $url")
        var webView: WebView? = null
        // Extra assurance it exits as it should.
        var shouldExit = false

        fun destroyWebView() {
            main {
                webView?.stopLoading()
                webView?.destroy()
                webView = null
                shouldExit = true
                Log.i(TAG, "Destroyed webview")
            }
        }

        var fixedRequest: WebViewRequest? = null
        val extraRequestList = threadSafeListOf<WebViewRequest>()

        main {
            // Useful for debugging
            WebView.setWebContentsDebuggingEnabled(true)
            try {
                webView = WebView(
                    (getContext() as? Context)
                        ?: throw RuntimeException("No base context in WebViewResolver")
                ).apply {
                    // Bare minimum to bypass captcha
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    webViewUserAgent = settings.userAgentString
                    // Don't set user agent, setting user agent will make cloudflare break.
                    if (userAgent != null) {
                        settings.userAgentString = userAgent
                    }
                }

                webView?.webViewClient = object : WebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest
                    ): WebResourceResponse? = runBlocking {
                        val webViewUrl = request.url.toString()
                        Log.i(TAG, "Loading WebView URL: $webViewUrl")

                        if (script != null) {
                            val handler = Handler(Looper.getMainLooper())
                            handler.post {
                                view.evaluateJavascript(script)
                                { scriptCallback?.invoke(it) }
                            }
                        }

                        if (interceptUrl.containsMatchIn(webViewUrl)) {
                            fixedRequest = request.toWebViewRequest()?.also {
                                requestCallBack(it)
                            }
                            Log.i(TAG, "Web-view request finished: $webViewUrl")
                            destroyWebView()
                            return@runBlocking null
                        }

                        if (additionalUrls.any { it.containsMatchIn(webViewUrl) }) {
                            request.toWebViewRequest()?.also {
                                if (requestCallBack(it)) destroyWebView()
                            }?.let(extraRequestList::add)
                        }

                        // Suppress image requests as we don't display them anywhere
                        val blacklistedFiles = listOf(
                            ".jpg", ".png", ".webp", ".mpg", ".mpeg",
                            ".jpeg", ".webm", ".mp4", ".mp3", ".gifv",
                            ".flv", ".asf", ".mov", ".mng", ".mkv",
                            ".ogg", ".avi", ".wav", ".woff2", ".woff",
                            ".ttf", ".css", ".vtt", ".srt", ".ts",
                            ".gif", "wss://"
                        )

                        return@runBlocking try {
                            when {
                                blacklistedFiles.any { URI(webViewUrl).path.contains(it) } || webViewUrl.endsWith(
                                    "/favicon.ico"
                                ) -> WebResourceResponse(
                                    "image/png",
                                    null,
                                    null
                                )

                                webViewUrl.contains("recaptcha") || webViewUrl.contains("/cdn-cgi/") -> super.shouldInterceptRequest(
                                    view,
                                    request
                                )

                                useOkhttp && request.method == "GET" -> {
                                    val resp = app.get(
                                        webViewUrl,
                                        headers = request.requestHeaders
                                    )
                                    resp.toWebResourceResponse()
                                }

                                useOkhttp && request.method == "POST" -> {
                                    val resp = app.post(
                                        webViewUrl,
                                        headers = request.requestHeaders
                                    )
                                    resp.toWebResourceResponse()
                                }

                                else -> super.shouldInterceptRequest(
                                    view,
                                    request
                                )
                            }
                        } catch (_: Exception) {
                            null
                        }
                    }

                    @SuppressLint("WebViewClientOnReceivedSslError")
                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        handler?.proceed() // Ignore ssl issues
                    }
                }
                webView?.loadUrl(url, headers)
            } catch (e: Exception) {
                logError(e)
            }
        }

        var loop = 0
        // Timeouts after this amount, 60s
        val totalTime = timeout

        val delayTime = 100L

        // A bit sloppy, but couldn't find a better way
        while (loop < totalTime / delayTime && !shouldExit) {
            if (fixedRequest != null) return fixedRequest to extraRequestList
            delay(delayTime)
            loop += 1
        }

        Log.i(TAG, "Web-view timeout after ${totalTime / 1000}s")
        destroyWebView()
        return fixedRequest to extraRequestList
    }
}

fun WebResourceRequest.toWebViewRequest(): WebViewRequest? {
    val webViewUrl = this.url.toString()
    return safe {
        WebViewRequest(
            url = webViewUrl,
            headers = this.requestHeaders ?: emptyMap(),
            method = this.method ?: "GET"
        )
    }
}

fun CloudStreamResponse.toWebResourceResponse(): WebResourceResponse {
    val contentTypeValue = this.headers["Content-Type"]
    val typeRegex = Regex("""(.*);(?:.*charset=(.*)(?:|;)|)""")
    return if (contentTypeValue != null) {
        val found = typeRegex.find(contentTypeValue)
        val contentType = found?.groupValues?.getOrNull(1)?.ifBlank { null } ?: contentTypeValue
        val charset = found?.groupValues?.getOrNull(2)?.ifBlank { null }
        WebResourceResponse(contentType, charset, this.text.byteInputStream())
    } else {
        WebResourceResponse("application/octet-stream", null, this.text.byteInputStream())
    }
}
