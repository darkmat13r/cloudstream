package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * CloudStream color palette inspired by Netflix, HBO Max, Apple TV+, YouTube.
 * Dark-first design with optional light theme support.
 */
@Immutable
data class CSColorScheme(
    // Backgrounds
    val background: Color,
    val backgroundElevated: Color,
    val surface: Color,
    val surfaceSecondary: Color,
    val surfaceTertiary: Color,
    val surfaceQuaternary: Color,
    val surfaceOverlay: Color,
    val surfaceFrosted: Color,

    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textQuaternary: Color,
    val textOnAccent: Color,
    val textLink: Color,

    // Accent
    val accentPrimary: Color,
    val accentSecondary: Color,
    val accentError: Color,
    val accentSuccess: Color,
    val accentWarning: Color,
    val accentInfo: Color,

    // Dividers & Borders
    val divider: Color,
    val borderCard: Color,
    val borderChip: Color,
    val borderSelected: Color,
    val borderFocus: Color,

    // Player
    val playerProgress: Color,
    val playerTrack: Color,
    val playerBuffer: Color,

    // Badges
    val badgeRating: Color,
    val badgeNew: Color,
    val badgeTop10: Color,

    // Functional
    val shimmerBase: Color,
    val shimmerHighlight: Color,
    val scrim: Color,

    val isLight: Boolean,
)

// --- Dark Theme ---
val CSDarkColors = CSColorScheme(
    // Backgrounds — Netflix/HBO Max pure black
    background = Color(0xFF000000),
    backgroundElevated = Color(0xFF0A0A0A),
    surface = Color(0xFF121212),
    surfaceSecondary = Color(0xFF1A1A1A),
    surfaceTertiary = Color(0xFF222222),
    surfaceQuaternary = Color(0xFF2E2E2E),
    surfaceOverlay = Color(0x99000000),      // 60% black
    surfaceFrosted = Color(0x8C141414),      // 55% charcoal

    // Text — high contrast white hierarchy
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB3B3B3),
    textTertiary = Color(0xFF8E8E93),
    textQuaternary = Color(0xFF666666),
    textOnAccent = Color(0xFF000000),
    textLink = Color(0xFF007AFF),

    // Accent — CloudStream brand indigo (differentiated from Netflix red / YouTube red)
    accentPrimary = Color(0xFF3D50FA),       // CloudStream brand blue
    accentSecondary = Color(0xFF9B51E0),     // Purple for exclusive/special
    accentError = Color(0xFFE53935),
    accentSuccess = Color(0xFF2ECC71),
    accentWarning = Color(0xFFFFD400),
    accentInfo = Color(0xFF00AAFF),

    // Dividers & Borders
    divider = Color(0xFF1F1F1F),
    borderCard = Color(0xFF2B2B2B),
    borderChip = Color(0xFF3A3A3A),
    borderSelected = Color(0xFF8A8A8A),
    borderFocus = Color(0xFFFFFFFF),

    // Player
    playerProgress = Color(0xFFE50914),      // Netflix-style red
    playerTrack = Color(0x33FFFFFF),
    playerBuffer = Color(0xFF555555),

    // Badges
    badgeRating = Color(0xFFFFD400),
    badgeNew = Color(0xFF2ECC71),
    badgeTop10 = Color(0xFFE50914),

    // Functional
    shimmerBase = Color(0xFF2A2A2A),
    shimmerHighlight = Color(0xFF3E3E3E),
    scrim = Color(0xCC000000),               // 80% black

    isLight = false,
)

// --- Light Theme ---
val CSLightColors = CSColorScheme(
    background = Color(0xFFFFFFFF),
    backgroundElevated = Color(0xFFF8F8F8),
    surface = Color(0xFFF0F0F0),
    surfaceSecondary = Color(0xFFE8E8E8),
    surfaceTertiary = Color(0xFFE0E0E0),
    surfaceQuaternary = Color(0xFFD0D0D0),
    surfaceOverlay = Color(0x66000000),
    surfaceFrosted = Color(0xDDF0F0F0),

    textPrimary = Color(0xFF000000),
    textSecondary = Color(0xFF606060),
    textTertiary = Color(0xFF9E9E9E),
    textQuaternary = Color(0xFFB0B0B0),
    textOnAccent = Color(0xFFFFFFFF),
    textLink = Color(0xFF065FD4),

    accentPrimary = Color(0xFF3D50FA),
    accentSecondary = Color(0xFF7B4DFF),
    accentError = Color(0xFFD32F2F),
    accentSuccess = Color(0xFF27AE60),
    accentWarning = Color(0xFFF39C12),
    accentInfo = Color(0xFF2196F3),

    divider = Color(0xFFE0E0E0),
    borderCard = Color(0xFFE0E0E0),
    borderChip = Color(0xFFCCCCCC),
    borderSelected = Color(0xFF666666),
    borderFocus = Color(0xFF3D50FA),

    playerProgress = Color(0xFFE50914),
    playerTrack = Color(0x33000000),
    playerBuffer = Color(0xFFCCCCCC),

    badgeRating = Color(0xFFF39C12),
    badgeNew = Color(0xFF27AE60),
    badgeTop10 = Color(0xFFE50914),

    shimmerBase = Color(0xFFE0E0E0),
    shimmerHighlight = Color(0xFFF5F5F5),
    scrim = Color(0x66000000),

    isLight = true,
)

// --- Gradient presets ---
object CSGradients {
    /** Hero bottom scrim for text legibility */
    val heroBottomScrim = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color(0xD9000000)),
    )

    /** Full card overlay scrim */
    val cardOverlay = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color(0x99000000)),
    )

    /** Top fade for status bar legibility */
    val topFade = Brush.verticalGradient(
        colors = listOf(Color(0x80000000), Color.Transparent),
    )

    /** Cinematic HBO-style warm gradient */
    val cinematicWarm = Brush.verticalGradient(
        colors = listOf(Color(0xFF050405), Color(0xFF2B0F0F), Color(0xFF441515)),
    )

    /** Shimmer sweep gradient */
    fun shimmer(base: Color, highlight: Color) = Brush.horizontalGradient(
        colors = listOf(base, highlight, base),
    )
}

val LocalCSColors = staticCompositionLocalOf { CSDarkColors }
