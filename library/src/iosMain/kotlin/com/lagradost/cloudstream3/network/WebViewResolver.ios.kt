package com.lagradost.cloudstream3.network

import io.ktor.client.request.*

/**
 * iOS stub for WebViewResolver. WebView is not available on iOS in this form.
 */
actual class WebViewResolver actual constructor(
    interceptUrl: Regex,
    additionalUrls: List<Regex>,
    userAgent: String?,
    useOkhttp: Boolean,
    script: String?,
    scriptCallback: ((String) -> Unit)?,
    timeout: Long
) : Interceptor {

    override suspend fun intercept(request: HttpRequestBuilder) {
        // No-op on iOS - WebView not available
    }

    actual companion object {
        actual val DEFAULT_TIMEOUT = 60_000L
        actual var webViewUserAgent: String? = null
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
        requestCallBack: (WebViewRequest) -> Boolean
    ): Pair<WebViewRequest?, List<WebViewRequest>> {
        return null to emptyList()
    }

    actual suspend fun resolveUsingWebView(
        request: WebViewRequest,
        requestCallBack: (WebViewRequest) -> Boolean
    ): Pair<WebViewRequest?, List<WebViewRequest>> {
        return null to emptyList()
    }
}
