package com.lagradost.cloudstream3.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleLanguageCode
import platform.Foundation.currentLocale
import platform.Foundation.localeIdentifier

actual fun getCurrentLocale(): String {
    return NSLocale.currentLocale.localeIdentifier.replace("-", "_")
}

actual fun getLocalizedLanguageName(ietfTag: String, nativeName: String, localizedTo: String?): String {
    val locale = if (localizedTo != null) {
        NSLocale(localeIdentifier = localizedTo.replace("_", "-"))
    } else {
        NSLocale.currentLocale
    }
    val displayName = locale.displayNameForKey(NSLocaleLanguageCode, ietfTag.split("-").first())
    return displayName ?: nativeName
}
