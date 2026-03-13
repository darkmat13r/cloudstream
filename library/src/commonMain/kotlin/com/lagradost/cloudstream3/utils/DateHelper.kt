package com.lagradost.cloudstream3.utils

/**
 * KMP-compatible date/time utilities.
 */
expect object DateHelper {
    /**
     * Parse a date string with the given format pattern and return epoch millis.
     * Returns null if parsing fails.
     */
    fun parseDate(dateString: String, format: String, locale: String = "en_US"): Long?

    /**
     * Format epoch millis to a date string with the given format pattern.
     */
    fun formatDate(epochMillis: Long, format: String, locale: String = "en_US"): String

    /**
     * Get the current year.
     */
    fun getCurrentYear(): Int

    /**
     * Get the current month (1-12).
     */
    fun getCurrentMonth(): Int

    /**
     * Get the current day of month.
     */
    fun getCurrentDay(): Int

    /**
     * Get current epoch millis.
     */
    fun currentTimeMillis(): Long

    /**
     * Extract year from a [java.util.Date]-style epoch millis value.
     */
    fun yearFromEpochMillis(epochMillis: Long): Int

    /**
     * Capitalize the first character of a string using the platform default locale.
     */
    fun capitalizeFirstChar(text: String): String
}
