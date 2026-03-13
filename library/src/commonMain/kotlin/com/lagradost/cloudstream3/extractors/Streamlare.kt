package com.lagradost.cloudstream3.extractors
import kotlinx.serialization.Serializable

import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.INFER_TYPE
import com.lagradost.cloudstream3.utils.newExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.network.RequestBodyTypes
import com.lagradost.cloudstream3.network.toMediaTypeOrNull
import com.lagradost.cloudstream3.network.toRequestBody


class Streamlare : Slmaxed() {
    override val mainUrl = "https://streamlare.com/"
}

open class Slmaxed : ExtractorApi() {
    override val name = "Streamlare"
    override val mainUrl = "https://slmaxed.com/"
    override val requiresReferer = true

    // https://slmaxed.com/e/oLvgezw3LjPzbp8E -> oLvgezw3LjPzbp8E
    val embedRegex = Regex("""/e/([^/]*)""")


    @Serializable
    data class JsonResponse(
        val status: String? = null,
        val message: String? = null,
        val type: String? = null,
        val token: String? = null,
        val result: Map<String, Result>? = null
    )

    @Serializable
    data class Result(
        val label: String? = null,
        val file: String? = null,
        val type: String? = null
    )

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val id = embedRegex.find(url)!!.groupValues[1]
        val json = app.post(
            "${mainUrl}api/video/stream/get",
            requestBody = """{"id":"$id"}""".toRequestBody(RequestBodyTypes.JSON.toMediaTypeOrNull())
        ).parsed<JsonResponse>()
        return json.result?.mapNotNull {
            it.value.let { result ->
                newExtractorLink(
                    this.name,
                    this.name,
                    result.file ?: return@mapNotNull null,
                    type = if (result.type?.contains(
                            "hls",
                            ignoreCase = true
                        ) == true
                    ) ExtractorLinkType.M3U8 else INFER_TYPE
                ) {
                    this.referer = url
                    this.quality =
                        result.label?.replace("p", "", ignoreCase = true)?.trim()?.toIntOrNull()
                            ?: Qualities.Unknown.value
                }
            }
        }
    }
}