package com.lagradost.cloudstream3.utils

import io.ktor.http.decodeURLPart
import io.ktor.http.encodeURLParameter

object StringUtils {
    fun String.encodeUri(): String {
        return this.encodeURLParameter()
    }

    fun String.decodeUri(): String {
        return this.decodeURLPart()
    }
}