package com.lagradost.cloudstream3.utils

import com.lagradost.cloudstream3.extractors.YoutubeExtractor
import com.lagradost.cloudstream3.extractors.YoutubeMobileExtractor
import com.lagradost.cloudstream3.extractors.YoutubeNoCookieExtractor
import com.lagradost.cloudstream3.extractors.YoutubeShortLinkExtractor

actual fun getPlatformExtractors(): List<ExtractorApi> = listOf(
    YoutubeExtractor(),
    YoutubeShortLinkExtractor(),
    YoutubeMobileExtractor(),
    YoutubeNoCookieExtractor(),
)
