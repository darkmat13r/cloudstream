package com.lagradost.cloudstream3.extractors

import com.lagradost.api.Log
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.base64Decode
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor
import io.ktor.http.Url

class Techinmind: GDMirrorbot() {
    override var name = "Techinmind Cloud AIO"
    override var mainUrl = "https://stream.techinmind.space"
    override var requiresReferer = true
}

open class GDMirrorbot : ExtractorApi() {
    override var name = "GDMirrorbot"
    override var mainUrl = "https://gdmirrorbot.nl"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val (sid, host) = if (!url.contains("key=")) {
            Pair(url.substringAfterLast("embed/"), getBaseUrl(app.get(url).url))
        } else {
            var pageText = app.get(url).text
            val finalId = Regex("""FinalID\s*=\s*"([^"]+)"""").find(pageText)?.groupValues?.get(1)
            val myKey = Regex("""myKey\s*=\s*"([^"]+)"""").find(pageText)?.groupValues?.get(1)
            val idType = Regex("""idType\s*=\s*"([^"]+)"""").find(pageText)?.groupValues?.get(1) ?: "imdbid"
            val baseUrl = Regex("""let\s+baseUrl\s*=\s*"([^"]+)"""").find(pageText)?.groupValues?.get(1)
            val hostUrl = baseUrl?.let { getBaseUrl(it) }

            if (finalId != null && myKey != null) {
                val apiUrl = if (url.contains("/tv/")) {
                    val season = Regex("""/tv/\d+/(\d+)/""").find(url)?.groupValues?.get(1) ?: "1"
                    val episode = Regex("""/tv/\d+/\d+/(\d+)""").find(url)?.groupValues?.get(1) ?: "1"
                    "$mainUrl/myseriesapi?tmdbid=$finalId&season=$season&epname=$episode&key=$myKey"
                } else {
                    "$mainUrl/mymovieapi?$idType=$finalId&key=$myKey"
                }
                pageText = app.get(apiUrl).text
            }

            val jsonElement = try { Json.parseToJsonElement(pageText) } catch (_: Exception) { return }
            val jsonObject = try { jsonElement.jsonObject } catch (_: Exception) { return }

            val embedId = url.substringAfterLast("/")
            val sidValue = jsonObject["data"]?.jsonArray
                ?.takeIf { it.size > 0 }
                ?.get(0)?.jsonObject
                ?.get("fileslug")?.jsonPrimitive?.content
                ?.takeIf { it.isNotBlank() } ?: embedId

            Pair(sidValue, hostUrl)
        }

        val postData = mapOf("sid" to sid)
        val responseText = app.post("$host/embedhelper.php", data = postData).text

        val rootElement = try { Json.parseToJsonElement(responseText) } catch (_: Exception) { return }
        val root = try { rootElement.jsonObject } catch (_: Exception) { return }

        val siteUrls = root["siteUrls"]?.jsonObject ?: return
        val siteFriendlyNames = try { root["siteFriendlyNames"]?.jsonObject } catch (_: Exception) { null }

        val decodedMresult = when {
            root["mresult"] != null && (try { root["mresult"]!!.jsonObject; true } catch (_: Exception) { false }) ->
                root["mresult"]!!.jsonObject
            root["mresult"] != null && (try { root["mresult"]!!.jsonPrimitive; true } catch (_: Exception) { false }) -> try {
                base64Decode(root["mresult"]!!.jsonPrimitive.content)
                    .let { Json.parseToJsonElement(it).jsonObject }
            } catch (e: Exception) {
                Log.e("GDMirrorbot", "Failed to decode mresult: $e")
                return
            }
            else -> return
        }

        siteUrls.keys.intersect(decodedMresult.keys).forEach { key ->
            val base = siteUrls[key]?.jsonPrimitive?.content?.trimEnd('/') ?: return@forEach
            val path = decodedMresult[key]?.jsonPrimitive?.content?.trimStart('/') ?: return@forEach
            val fullUrl = "$base/$path"
            val friendlyName = siteFriendlyNames?.get(key)?.jsonPrimitive?.content ?: key

            try {
                when (friendlyName) {
                    "StreamHG","EarnVids" -> VidHidePro().getUrl(fullUrl, referer, subtitleCallback, callback)
                    "RpmShare", "UpnShare", "StreamP2p" -> VidStack().getUrl(fullUrl, referer, subtitleCallback, callback)
                    else -> loadExtractor(fullUrl, referer ?: mainUrl, subtitleCallback, callback)
                }
            } catch (e: Exception) {
                Log.e("GDMirrorbot", "Failed to extract from $friendlyName at $fullUrl: $e")
            }
        }
    }

    private fun getBaseUrl(url: String): String {
        return Url(url).let { "${it.protocol.name}://${it.host}" }
    }
}

