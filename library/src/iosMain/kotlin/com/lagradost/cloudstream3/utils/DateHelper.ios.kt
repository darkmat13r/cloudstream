package com.lagradost.cloudstream3.utils

import platform.Foundation.*

actual object DateHelper {
    // NSDate reference date (Jan 1, 2001) is 978307200 seconds after Unix epoch (Jan 1, 1970)
    private const val REFERENCE_DATE_OFFSET = 978307200.0

    private fun nsDateFromEpochMillis(epochMillis: Long): NSDate {
        val epochSeconds = epochMillis / 1000.0
        return NSDate(timeIntervalSinceReferenceDate = epochSeconds - REFERENCE_DATE_OFFSET)
    }

    actual fun parseDate(dateString: String, format: String, locale: String): Long? {
        val formatter = NSDateFormatter()
        formatter.dateFormat = format
        formatter.locale = NSLocale(localeIdentifier = locale.replace("_", "-"))
        val date = formatter.dateFromString(dateString) ?: return null
        return (date.timeIntervalSince1970 * 1000).toLong()
    }

    actual fun formatDate(epochMillis: Long, format: String, locale: String): String {
        val formatter = NSDateFormatter()
        formatter.dateFormat = format
        formatter.locale = NSLocale(localeIdentifier = locale.replace("_", "-"))
        val date = nsDateFromEpochMillis(epochMillis)
        return formatter.stringFromDate(date)
    }

    actual fun getCurrentYear(): Int {
        val cal = NSCalendar.currentCalendar
        val components = cal.components(NSCalendarUnitYear, NSDate())
        return components.year.toInt()
    }

    actual fun getCurrentMonth(): Int {
        val cal = NSCalendar.currentCalendar
        val components = cal.components(NSCalendarUnitMonth, NSDate())
        return components.month.toInt()
    }

    actual fun getCurrentDay(): Int {
        val cal = NSCalendar.currentCalendar
        val components = cal.components(NSCalendarUnitDay, NSDate())
        return components.day.toInt()
    }

    actual fun currentTimeMillis(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }

    actual fun yearFromEpochMillis(epochMillis: Long): Int {
        val date = nsDateFromEpochMillis(epochMillis)
        val cal = NSCalendar.currentCalendar
        val components = cal.components(NSCalendarUnitYear, date)
        return components.year.toInt()
    }

    actual fun capitalizeFirstChar(text: String): String {
        return text.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
