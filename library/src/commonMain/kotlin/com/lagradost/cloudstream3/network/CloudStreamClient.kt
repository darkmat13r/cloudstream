package com.lagradost.cloudstream3.network

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.reflect.KClass

/**
 * KMP-compatible interceptor for modifying HTTP requests.
 * Replaces okhttp3.Interceptor for cross-platform compatibility.
 */
fun interface Interceptor {
    suspend fun intercept(request: HttpRequestBuilder)
}

/**
 * Content type constants for request bodies.
 * Drop-in replacement for com.lagradost.nicehttp.RequestBodyTypes.
 */
object RequestBodyTypes {
    const val JSON = "application/json;charset=utf-8"
    const val TEXT = "text/plain;charset=utf-8"
}

/**
 * KMP-compatible request body.
 * Replaces okhttp3.RequestBody.
 */
data class CloudStreamRequestBody(
    val data: String,
    val contentType: String = RequestBodyTypes.TEXT
)

/**
 * Extension to create a CloudStreamRequestBody from a String.
 * Replaces okhttp3.RequestBody.Companion.toRequestBody.
 */
fun String.toRequestBody(contentType: String? = null): CloudStreamRequestBody {
    return CloudStreamRequestBody(this, contentType ?: RequestBodyTypes.TEXT)
}

/**
 * Backward-compatibility shim for okhttp3.MediaType.Companion.toMediaTypeOrNull.
 * Simply returns the string itself since CloudStreamRequestBody accepts String content types.
 */
fun String.toMediaTypeOrNull(): String? = this

/**
 * Simple HTTP request representation for KMP.
 * Replaces okhttp3.Request in WebView-related APIs.
 */
data class WebViewRequest(
    val url: String,
    val headers: Map<String, String> = emptyMap(),
    val method: String = "GET"
)

/**
 * JSON parser interface for HTTP responses.
 * Same interface as com.lagradost.nicehttp.ResponseParser.
 */
interface ResponseParser {
    fun <T : Any> parse(text: String, kClass: KClass<T>): T
    fun <T : Any> parseSafe(text: String, kClass: KClass<T>): T?
    fun writeValueAsString(obj: Any): String
}

/**
 * HTTP response wrapper for KMP.
 * Drop-in replacement for com.lagradost.nicehttp.NiceResponse.
 */
class CloudStreamResponse(
    private val rawBytes: ByteArray,
    private val responseUrl: String,
    private val statusCode: Int,
    private val responseHeaders: Headers,
    private val successful: Boolean,
    @PublishedApi internal val parser: ResponseParser?
) {
    val text: String by lazy { rawBytes.decodeToString() }
    val url: String get() = responseUrl
    val code: Int get() = statusCode
    val isSuccessful: Boolean get() = successful
    val headers: Headers get() = responseHeaders

    /** Raw response body as an object with .bytes() method for binary data access */
    val body: ResponseBody = ResponseBody(rawBytes)

    val document: Document by lazy { Ksoup.parse(html = text) }

    val cookies: Map<String, String> by lazy {
        responseHeaders.getAll("Set-Cookie")?.associate { cookie ->
            val parts = cookie.split(";").first().split("=", limit = 2)
            (parts.getOrNull(0) ?: "") to (parts.getOrNull(1) ?: "")
        } ?: emptyMap()
    }

    inline fun <reified T : Any> parsed(): T {
        return parser!!.parse(text, T::class)
    }

    inline fun <reified T : Any> parsedSafe(): T? {
        return parser?.parseSafe(text, T::class)
    }

    override fun toString(): String = text
}

/** Simple wrapper for raw response bytes, compatible with NiceHttp's body.bytes() pattern. */
class ResponseBody(private val data: ByteArray) {
    fun bytes(): ByteArray = data
    fun string(): String = data.decodeToString()
    val size: Long get() = data.size.toLong()
}

/** Extension to convert Ktor Headers to a flat Map (first value per key). */
fun Headers.toMap(): Map<String, String> =
    entries().associate { (name, values) -> name to (values.firstOrNull() ?: "") }

/**
 * KMP HTTP client wrapping Ktor.
 * Drop-in replacement for com.lagradost.nicehttp.Requests.
 */
open class CloudStreamClient(
    var defaultHeaders: Map<String, String> = mapOf(),
    var responseParser: ResponseParser? = null,
) {
    var client: HttpClient = HttpClient {
        followRedirects = true
    }

    /**
     * Platform-specific HTTP client reference.
     * On Android/JVM, this is an OkHttpClient for use with ExoPlayer, CloudflareKiller, etc.
     */
    var baseClient: Any? = null

    private suspend fun execute(
        method: HttpMethod,
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        data: Map<String, String>? = null,
        json: Any? = null,
        requestBody: CloudStreamRequestBody? = null,
        allowRedirects: Boolean = true,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        parser: ResponseParser? = null,
    ): CloudStreamResponse {
        val response = client.request(url) {
            this.method = method

            // Add query parameters
            params.forEach { (key, value) ->
                parameter(key, value)
            }

            // Add default headers
            defaultHeaders.forEach { (key, value) ->
                header(key, value)
            }

            // Add request-specific headers
            headers.forEach { (key, value) ->
                header(key, value)
            }

            // Add referer
            if (referer != null) {
                header("Referer", referer)
            }

            // Add cookies
            if (cookies.isNotEmpty()) {
                header(
                    "Cookie",
                    cookies.entries.joinToString("; ") { "${it.key}=${it.value}" })
            }

            // Apply interceptor
            interceptor?.intercept(this)

            // Set body
            when {
                requestBody != null -> {
                    contentType(ContentType.parse(requestBody.contentType))
                    setBody(requestBody.data)
                }

                json != null -> {
                    contentType(ContentType.Application.Json)
                    val jsonString = if (json is String) json
                    else responseParser?.writeValueAsString(json) ?: json.toString()
                    setBody(jsonString)
                }

                data != null && data.isNotEmpty() -> {
                    setBody(FormDataContent(Parameters.build {
                        data.forEach { (key, value) -> append(key, value) }
                    }))
                }
            }
        }

        return CloudStreamResponse(
            rawBytes = response.readRawBytes(),
            responseUrl = response.request.url.toString(),
            statusCode = response.status.value,
            responseHeaders = response.headers,
            successful = response.status.isSuccess(),
            parser = parser ?: responseParser
        )
    }

    suspend fun get(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        allowRedirects: Boolean = true,
        @Suppress("UNUSED_PARAMETER") cacheTime: Int = 0,
        @Suppress("UNUSED_PARAMETER") cacheUnit: Any? = null,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        responseParser: ResponseParser? = this.responseParser,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Get,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
        parser = responseParser
    )

    suspend fun post(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        data: Map<String, String>? = null,
        json: Any? = null,
        requestBody: CloudStreamRequestBody? = null,
        allowRedirects: Boolean = true,
        @Suppress("UNUSED_PARAMETER") cacheTime: Int = 0,
        @Suppress("UNUSED_PARAMETER") cacheUnit: Any? = null,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        responseParser: ResponseParser? = this.responseParser,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Post,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        data = data, json = json, requestBody = requestBody,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
        parser = responseParser
    )

    suspend fun head(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        allowRedirects: Boolean = true,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Head,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
    )

    suspend fun put(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        data: Map<String, String>? = null,
        json: Any? = null,
        requestBody: CloudStreamRequestBody? = null,
        allowRedirects: Boolean = true,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        responseParser: ResponseParser? = this.responseParser,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Put,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        data = data, json = json, requestBody = requestBody,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
        parser = responseParser
    )

    suspend fun delete(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        data: Map<String, String>? = null,
        json: Any? = null,
        requestBody: CloudStreamRequestBody? = null,
        allowRedirects: Boolean = true,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        responseParser: ResponseParser? = this.responseParser,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Delete,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        data = data, json = json, requestBody = requestBody,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
        parser = responseParser
    )

    suspend fun patch(
        url: String,
        headers: Map<String, String> = mapOf(),
        referer: String? = null,
        params: Map<String, String> = mapOf(),
        cookies: Map<String, String> = mapOf(),
        data: Map<String, String>? = null,
        json: Any? = null,
        requestBody: CloudStreamRequestBody? = null,
        allowRedirects: Boolean = true,
        timeout: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        responseParser: ResponseParser? = this.responseParser,
    ): CloudStreamResponse = execute(
        method = HttpMethod.Patch,
        url = url, headers = headers, referer = referer,
        params = params, cookies = cookies,
        data = data, json = json, requestBody = requestBody,
        allowRedirects = allowRedirects, timeout = timeout,
        interceptor = interceptor, verify = verify,
        parser = responseParser
    )
}
