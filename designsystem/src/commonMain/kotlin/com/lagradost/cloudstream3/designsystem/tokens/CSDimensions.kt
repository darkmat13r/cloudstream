package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CloudStream spacing & sizing tokens.
 * Based on streaming platform analysis (4/8/12/16/24/32px scale).
 */
@Immutable
data class CSSpacing(
    /** 4dp — Icon-to-label gap, inline spacing */
    val xxs: Dp = 4.dp,
    /** 6dp — Tight padding (badge internal) */
    val xs: Dp = 6.dp,
    /** 8dp — Card internal, chip padding, grid gap */
    val sm: Dp = 8.dp,
    /** 12dp — Card gaps, icon button padding */
    val md: Dp = 12.dp,
    /** 16dp — Screen edges, section padding, card gaps */
    val lg: Dp = 16.dp,
    /** 20dp — Vertical spacing between cards */
    val xl: Dp = 20.dp,
    /** 24dp — Section vertical spacing */
    val xxl: Dp = 24.dp,
    /** 32dp — Major section breaks */
    val xxxl: Dp = 32.dp,
    /** 48dp — Hero-to-content spacing */
    val xxxxl: Dp = 48.dp,
)

/** Size tokens for common UI elements */
@Immutable
data class CSSizes(
    // Icons
    val iconXs: Dp = 12.dp,
    val iconSm: Dp = 16.dp,
    val iconMd: Dp = 20.dp,
    val iconLg: Dp = 24.dp,
    val iconXl: Dp = 28.dp,
    val iconXxl: Dp = 36.dp,
    val iconPlayer: Dp = 56.dp,

    // Touch targets
    val touchTargetMin: Dp = 44.dp,
    val touchTargetLg: Dp = 48.dp,
    val touchTargetPlayer: Dp = 56.dp,

    // Navigation
    val bottomBarHeight: Dp = 56.dp,
    val topBarHeight: Dp = 56.dp,
    val sideRailWidth: Dp = 72.dp,
    val sideRailExpandedWidth: Dp = 240.dp,
    val tabBarLabelSize: Dp = 10.dp,

    // Cards
    val posterWidthSm: Dp = 100.dp,
    val posterWidthMd: Dp = 120.dp,
    val posterWidthLg: Dp = 140.dp,
    val landscapeWidthSm: Dp = 160.dp,
    val landscapeWidthMd: Dp = 200.dp,
    val landscapeWidthLg: Dp = 240.dp,
    val episodeThumbnailWidth: Dp = 140.dp,

    // Avatars
    val avatarSm: Dp = 32.dp,
    val avatarMd: Dp = 48.dp,
    val avatarLg: Dp = 64.dp,
    val avatarXl: Dp = 80.dp,

    // Player
    val progressBarHeight: Dp = 4.dp,
    val scrubberSize: Dp = 14.dp,

    // Search
    val searchBarHeight: Dp = 44.dp,
    val chipHeight: Dp = 34.dp,
    val categoryTileHeight: Dp = 90.dp,

    // Button
    val buttonHeight: Dp = 48.dp,
    val buttonHeightSm: Dp = 36.dp,

    // Focus ring
    val focusRingWidth: Dp = 3.dp,
    val focusRingOffset: Dp = 4.dp,
    val focusScaleFactor: Float = 1.05f,

    // TV-specific sizes
    val tvPosterWidth: Dp = 180.dp,
    val tvLandscapeWidth: Dp = 320.dp,
    val tvIconSize: Dp = 32.dp,
    val tvFocusRingWidth: Dp = 4.dp,
)

/** Elevation tokens */
@Immutable
data class CSElevation(
    val none: Dp = 0.dp,
    val xs: Dp = 1.dp,
    val sm: Dp = 2.dp,
    val md: Dp = 4.dp,
    val lg: Dp = 8.dp,
    val xl: Dp = 16.dp,
)

/** Alpha/opacity defaults */
object AlphaDefaults {
    const val Disabled = 0.38f
    const val Medium = 0.60f
    const val High = 0.87f
    const val Full = 1.0f
    const val Scrim = 0.60f
    const val FrostedGlass = 0.55f
    const val Overlay = 0.40f
    const val HoverHighlight = 0.08f
    const val PressedHighlight = 0.12f
    const val FocusHighlight = 0.16f
}

val LocalCSSpacing = staticCompositionLocalOf { CSSpacing() }
val LocalCSSizes = staticCompositionLocalOf { CSSizes() }
val LocalCSElevation = staticCompositionLocalOf { CSElevation() }
