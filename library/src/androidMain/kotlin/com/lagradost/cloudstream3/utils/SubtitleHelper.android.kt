package com.lagradost.cloudstream3.utils

import java.util.Locale

actual fun getCurrentLocale(): String {
    return Locale.getDefault().toLanguageTag()
}

actual fun getLocalizedLanguageName(ietfTag: String, nativeName: String, localizedTo: String?): String {
    val localeOfLangCode = Locale.forLanguageTag(ietfTag)
    val localeOfLocalizeTo = Locale.forLanguageTag(localizedTo ?: getCurrentLocale())
    val sysLocalizedName = localeOfLangCode.getDisplayName(localeOfLocalizeTo)

    val langCodeWithCountry = "${localeOfLangCode.language} ("
    val failedToLocalize =
        sysLocalizedName.equals(ietfTag, ignoreCase = true) ||
        sysLocalizedName.contains(langCodeWithCountry, ignoreCase = true)

    return if (failedToLocalize) nativeName else sysLocalizedName
}
