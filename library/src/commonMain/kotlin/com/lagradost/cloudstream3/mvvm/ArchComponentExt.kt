package com.lagradost.cloudstream3.mvvm

import com.lagradost.api.BuildConfig
import com.lagradost.api.Log
import com.lagradost.cloudstream3.ErrorLoadingException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Checks if this throwable is a network timeout-related exception.
 * Matches JVM's SocketTimeoutException and InterruptedIOException by class name
 * to remain KMP-compatible.
 */
private fun Throwable.isTimeoutException(): Boolean {
    val name = this::class.simpleName ?: return false
    return name == "SocketTimeoutException" || name == "InterruptedIOException" || name == "ConnectTimeoutException"
}

/**
 * Checks if this throwable is an unknown host exception (DNS resolution failure).
 */
private fun Throwable.isUnknownHostException(): Boolean {
    val name = this::class.simpleName ?: return false
    return name == "UnknownHostException"
}

/**
 * Checks if this throwable is an SSL handshake exception.
 */
private fun Throwable.isSSLHandshakeException(): Boolean {
    val name = this::class.simpleName ?: return false
    return name == "SSLHandshakeException"
}

const val DEBUG_EXCEPTION = "THIS IS A DEBUG EXCEPTION!"
const val DEBUG_PRINT = "DEBUG PRINT"

class DebugException(message: String) : Exception("$DEBUG_EXCEPTION\n$message")

inline fun debugException(message: () -> String) {
    if (BuildConfig.DEBUG) {
        throw DebugException(message.invoke())
    }
}

inline fun debugPrint(tag: String = DEBUG_PRINT, message: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message.invoke())
    }
}

inline fun debugWarning(message: () -> String) {
    if (BuildConfig.DEBUG) {
        logError(DebugException(message.invoke()))
    }
}

inline fun debugAssert(assert: () -> Boolean, message: () -> String) {
    if (BuildConfig.DEBUG && assert.invoke()) {
        throw DebugException(message.invoke())
    }
}

inline fun debugWarning(assert: () -> Boolean, message: () -> String) {
    if (BuildConfig.DEBUG && assert.invoke()) {
        logError(DebugException(message.invoke()))
    }
}

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorString: String,
    ) : Resource<Nothing>()

    data class Loading(val url: String? = null) : Resource<Nothing>()

    companion object {
        fun <T> fromResult(result: Result<T>) : Resource<T> {
            val value = result.getOrNull()
            return if(value != null) {
                Success(value)
            } else {
                throwAbleToResource(result.exceptionOrNull() ?: Exception("this should not be possible"))
            }
        }
    }
}

fun logError(throwable: Throwable) {
    Log.d("ApiError", "-------------------------------------------------------------------")
    Log.d("ApiError", "safeApiCall: " + (throwable.message ?: "Unknown error"))
    Log.d("ApiError", "safeApiCall: " + throwable.message)
    throwable.printStackTrace()
    Log.d("ApiError", "-------------------------------------------------------------------")
}

@Deprecated(
    "Outdated function, use `safe` instead",
    replaceWith = ReplaceWith("safe"),
    level = DeprecationLevel.ERROR
)
fun <T> normalSafeApiCall(apiCall: () -> T): T? {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        logError(throwable)
        return null
    }
}

/** Catches any exception (or error) and only logs it.
 * Will return null on exceptions. */
fun <T> safe(apiCall: () -> T): T? {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        logError(throwable)
        return null
    }
}

/** Catches any exception (or error) and only logs it.
 * Will return null on exceptions. */
suspend fun <T> safeAsync(apiCall: suspend () -> T): T? {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        logError(throwable)
        return null
    }
}

@Deprecated(
    "Outdated function, use `safeAsync` instead",
    replaceWith = ReplaceWith("safeAsync"),
    level = DeprecationLevel.ERROR
)
suspend fun <T> suspendSafeApiCall(apiCall: suspend () -> T): T? {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        logError(throwable)
        return null
    }
}

fun Throwable.getAllMessages(): String {
    return (this.message ?: "") + (this.cause?.getAllMessages()?.let { "\n$it" } ?: "")
}

fun Throwable.getStackTracePretty(showMessage: Boolean = true): String {
    val prefix = if (showMessage) this.message?.let { "\n$it" } ?: "" else ""
    return prefix + this.stackTraceToString()
}

fun <T> safeFail(throwable: Throwable): Resource<T> {
    val stackTraceMsg = throwable.getStackTracePretty()
    return Resource.Failure(false, stackTraceMsg)
}

fun CoroutineScope.launchSafe(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val obj: suspend CoroutineScope.() -> Unit = {
        try {
            block()
        } catch (throwable: Throwable) {
            logError(throwable)
        }
    }

    return this.launch(context, start, obj)
}

fun <T> throwAbleToResource(
    throwable: Throwable
): Resource<T> {
    return when {
        throwable::class.simpleName == "NoSuchMethodError" ||
            throwable::class.simpleName == "NoSuchFieldError" ||
            throwable::class.simpleName == "NoSuchMethodException" ||
            throwable::class.simpleName == "NoSuchFieldException" -> {
            Resource.Failure(
                false,
                "App or extension is outdated, update the app or try pre-release.\n${throwable.message}" // todo add exact version?
            )
        }

        throwable is NullPointerException -> {
            // stackTrace is JVM-only; try to extract info from the message
            val providerInfo = throwable.stackTraceToString().lines().firstOrNull { line ->
                line.contains("provider.kt", ignoreCase = true)
            }
            if (providerInfo != null) {
                Resource.Failure(
                    false,
                    "NullPointerException in provider\nSite might have updated or added Cloudflare/DDOS protection"
                )
            } else {
                safeFail(throwable)
            }
        }

        throwable.isTimeoutException() -> {
            Resource.Failure(
                true,
                "Connection Timeout\nPlease try again later."
            )
        }

        throwable.isUnknownHostException() -> {
            Resource.Failure(
                true,
                "Cannot connect to server, try again later.\n${throwable.message}"
            )
        }

        throwable is ErrorLoadingException -> {
            Resource.Failure(
                true,
                throwable.message ?: "Error loading, try again later."
            )
        }

        throwable is NotImplementedError -> {
            Resource.Failure(false, "This operation is not implemented.")
        }

        throwable.isSSLHandshakeException() -> {
            Resource.Failure(
                true,
                (throwable.message ?: "SSLHandshakeException") + "\nTry a VPN or DNS."
            )
        }

        throwable is CancellationException -> {
            throwable.cause?.let {
                throwAbleToResource(it)
            } ?: safeFail(throwable)
        }

        else -> safeFail(throwable)
    }
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            logError(throwable)
            throwAbleToResource(throwable)
        }
    }
}
