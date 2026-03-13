package com.lagradost.cloudstream3.network

import android.content.Context
import androidx.preference.PreferenceManager
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.USER_AGENT
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.mvvm.safe
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.Cache
import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders
import okhttp3.OkHttpClient
import okhttp3.Request
import org.conscrypt.Conscrypt
import java.io.File
import java.security.SecureRandom
import java.security.Security
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun CloudStreamClient.initClient(context: Context) {
    val okHttpClient = buildDefaultClient(context)
    app.baseClient = okHttpClient
    app.client = HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
        }
    }
}

/**
 * Disables SSL certificate verification for the OkHttpClient.Builder.
 * Replaces com.lagradost.nicehttp.ignoreAllSSLErrors.
 */
fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })
    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, SecureRandom())
    }
    sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
    hostnameVerifier { _, _ -> true }
    return this
}

fun buildDefaultClient(context: Context): OkHttpClient {
    safe { Security.insertProviderAt(Conscrypt.newProvider(), 1) }

    val settingsManager = PreferenceManager.getDefaultSharedPreferences(context)
    val dns = settingsManager.getInt(context.getString(R.string.dns_pref), 0)
    val baseClient = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .ignoreAllSSLErrors()
        .cache(
            // Note that you need to add a ResponseInterceptor to make this 100% active.
            // The server response dictates if and when stuff should be cached.
            Cache(
                directory = File(context.cacheDir, "http_cache"),
                maxSize = 50L * 1024L * 1024L // 50 MiB
            )
        ).apply {
            when (dns) {
                1 -> addGoogleDns()
                2 -> addCloudFlareDns()
//                3 -> addOpenDns()
                4 -> addAdGuardDns()
                5 -> addDNSWatchDns()
                6 -> addQuad9Dns()
                7 -> addDnsSbDns()
                8 -> addCanadianShieldDns()
            }
        }
        // Needs to be build as otherwise the other builders will change this object
        .build()
    return baseClient
}

//val Request.cookies: Map<String, String>
//    get() {
//        return this.headers.getCookies("Cookie")
//    }

/**
 * Extension property to parse cookies from an OkHttp Request's Cookie header.
 * Replaces com.lagradost.nicehttp.cookies extension.
 */
val Request.cookies: Map<String, String>
    get() {
        val cookieHeader = header("Cookie") ?: return emptyMap()
        return cookieHeader.split(";").associate {
            val split = it.split("=", limit = 2)
            (split.getOrNull(0)?.trim() ?: "") to (split.getOrNull(1)?.trim() ?: "")
        }.filter { it.key.isNotBlank() && it.value.isNotBlank() }
    }

private val DEFAULT_HEADERS = mapOf("user-agent" to USER_AGENT)

/**
 * Set headers > Set cookies > Default headers > Default Cookies
 * TODO REMOVE AND REPLACE WITH NICEHTTP
 */
fun getHeaders(
    headers: Map<String, String>,
    cookie: Map<String, String>
): Headers {
    val cookieMap =
        if (cookie.isNotEmpty()) mapOf(
            "Cookie" to cookie.entries.joinToString(" ") {
                "${it.key}=${it.value};"
            }) else mapOf()
    val tempHeaders = (DEFAULT_HEADERS + headers + cookieMap)
    return tempHeaders.toHeaders()
}
