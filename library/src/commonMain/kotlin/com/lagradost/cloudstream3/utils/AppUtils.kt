package com.lagradost.cloudstream3.utils

import com.lagradost.cloudstream3.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.serializer

object AppUtils {
    /** Any object as json string */
    inline fun <reified T> T.toJson(): String {
        if (this is String) return this
        return json.encodeToString(this)
    }

    inline fun <reified T> parseJson(value: String): T {
        return json.decodeFromString(value)
    }

    inline fun <reified T> tryParseJson(value: String?): T? {
        return try {
            parseJson(value ?: return null)
        } catch (_: Exception) {
            null
        }
    }
}