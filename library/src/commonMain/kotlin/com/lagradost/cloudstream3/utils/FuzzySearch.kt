package com.lagradost.cloudstream3.utils

import kotlin.math.min

/**
 * Simple fuzzy string matching utilities using Levenshtein distance.
 * Replaces me.xdrop:fuzzywuzzy for KMP compatibility.
 */
object FuzzySearch {
    /**
     * Calculates the similarity ratio between two strings (0-100).
     * Based on normalized Levenshtein distance.
     */
    fun ratio(s1: String, s2: String): Int {
        if (s1 == s2) return 100
        if (s1.isEmpty() || s2.isEmpty()) return 0

        val distance = levenshteinDistance(s1, s2)
        val maxLen = maxOf(s1.length, s2.length)
        return ((1.0 - distance.toDouble() / maxLen) * 100).toInt()
    }

    /**
     * Calculates the best partial match ratio (0-100).
     * Finds the best matching substring of the longer string
     * against the shorter string.
     */
    fun partialRatio(s1: String, s2: String): Int {
        if (s1 == s2) return 100
        if (s1.isEmpty() || s2.isEmpty()) return 0

        val shorter: String
        val longer: String
        if (s1.length <= s2.length) {
            shorter = s1
            longer = s2
        } else {
            shorter = s2
            longer = s1
        }

        if (longer.contains(shorter)) return 100

        var bestScore = 0
        for (i in 0..longer.length - shorter.length) {
            val sub = longer.substring(i, i + shorter.length)
            val score = ratio(shorter, sub)
            if (score > bestScore) {
                bestScore = score
                if (bestScore == 100) return 100
            }
        }
        return bestScore
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length

        var prevRow = IntArray(n + 1) { it }
        var currRow = IntArray(n + 1)

        for (i in 1..m) {
            currRow[0] = i
            for (j in 1..n) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                currRow[j] = min(
                    min(currRow[j - 1] + 1, prevRow[j] + 1),
                    prevRow[j - 1] + cost
                )
            }
            val temp = prevRow
            prevRow = currRow
            currRow = temp
        }
        return prevRow[n]
    }
}
