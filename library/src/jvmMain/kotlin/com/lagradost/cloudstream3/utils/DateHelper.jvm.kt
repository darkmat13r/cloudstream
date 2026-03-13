package com.lagradost.cloudstream3.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

actual object DateHelper {
    actual fun parseDate(dateString: String, format: String, locale: String): Long? {
        return try {
            val parts = locale.split("_")
            val javaLocale = if (parts.size >= 2) Locale(parts[0], parts[1]) else Locale(parts[0])
            SimpleDateFormat(format, javaLocale).parse(dateString)?.time
        } catch (e: Exception) {
            null
        }
    }

    actual fun formatDate(epochMillis: Long, format: String, locale: String): String {
        val parts = locale.split("_")
        val javaLocale = if (parts.size >= 2) Locale(parts[0], parts[1]) else Locale(parts[0])
        return SimpleDateFormat(format, javaLocale).format(Date(epochMillis))
    }

    actual fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
    actual fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1
    actual fun getCurrentDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    actual fun currentTimeMillis(): Long = System.currentTimeMillis()

    actual fun yearFromEpochMillis(epochMillis: Long): Int {
        return Calendar.getInstance().apply {
            time = Date(epochMillis)
        }.get(Calendar.YEAR)
    }

    actual fun capitalizeFirstChar(text: String): String {
        return text.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}
