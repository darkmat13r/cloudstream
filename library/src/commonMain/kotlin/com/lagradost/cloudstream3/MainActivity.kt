package com.lagradost.cloudstream3

import com.lagradost.cloudstream3.network.CloudStreamClient
import com.lagradost.cloudstream3.network.ResponseParser
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

// Short name for requests client to make it nicer to use

@OptIn(InternalSerializationApi::class)
var app = CloudStreamClient(responseParser = object : ResponseParser {
    override fun <T : Any> parse(text: String, kClass: KClass<T>): T {
        return json.decodeFromString(kClass.serializer(), text)
    }

    override fun <T : Any> parseSafe(text: String, kClass: KClass<T>): T? {
        return try {
            json.decodeFromString(kClass.serializer(), text)
        } catch (e: Exception) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun writeValueAsString(obj: Any): String {
        val serializer = (obj::class as KClass<Any>).serializer()
        return json.encodeToString(serializer, obj)
    }
}).apply {
    defaultHeaders = mapOf("user-agent" to USER_AGENT)
}
