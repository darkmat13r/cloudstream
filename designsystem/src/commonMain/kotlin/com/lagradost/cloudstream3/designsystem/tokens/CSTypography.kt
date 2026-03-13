package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * CloudStream typography scale derived from Netflix, HBO Max, Apple TV+, YouTube.
 * Uses system sans-serif (Roboto on Android, SF Pro on iOS).
 */
@Immutable
data class CSTypographyScale(
    /** 48sp / ExtraBold — Hero show titles, onboarding headlines */
    val displayLarge: TextStyle,
    /** 36sp / Bold — Hero headlines, section features */
    val displayMedium: TextStyle,
    /** 30sp / Bold — Large section headings, profile names */
    val displaySmall: TextStyle,

    /** 26sp / Bold — Page titles ("Choose Your Plan") */
    val headlineLarge: TextStyle,
    /** 22sp / SemiBold — Section headers, nav titles */
    val headlineMedium: TextStyle,
    /** 18sp / SemiBold — Subsection titles, card titles */
    val headlineSmall: TextStyle,

    /** 16sp / SemiBold — Episode titles, list item headings */
    val titleLarge: TextStyle,
    /** 14sp / Medium — Button labels, chip text, tab labels */
    val titleMedium: TextStyle,
    /** 12sp / Medium — Small titles, metadata labels */
    val titleSmall: TextStyle,

    /** 16sp / Regular — Synopsis, descriptions, feature bullets */
    val bodyLarge: TextStyle,
    /** 14sp / Regular — Card descriptions, metadata */
    val bodyMedium: TextStyle,
    /** 13sp / Regular — Episode descriptions, secondary info */
    val bodySmall: TextStyle,

    /** 12sp / Regular — Timestamps, durations, badge text */
    val caption: TextStyle,
    /** 11sp / Medium — "NEW EPISODE", uppercase labels */
    val overline: TextStyle,
)

val CSDefaultTypography = CSTypographyScale(
    displayLarge = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 52.sp,
        letterSpacing = (-0.5).sp,
    ),
    displayMedium = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        letterSpacing = (-0.25).sp,
    ),
    displaySmall = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 34.sp,
    ),
    headlineLarge = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
    ),
    overline = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 14.sp,
        letterSpacing = 1.sp,
    ),
)

val LocalCSTypography = staticCompositionLocalOf { CSDefaultTypography }
