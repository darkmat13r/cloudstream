package com.lagradost.cloudstream3.designsystem.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Window size classes for responsive layouts across phone, tablet, and TV.
 */
enum class WindowWidthClass {
    /** Phone portrait: < 600dp */
    Compact,
    /** Tablet portrait / phone landscape: 600–839dp */
    Medium,
    /** Tablet landscape / desktop / TV: >= 840dp */
    Expanded,
}

enum class WindowHeightClass {
    Compact,
    Medium,
    Expanded,
}

@Immutable
data class WindowSizeClass(
    val widthClass: WindowWidthClass,
    val heightClass: WindowHeightClass,
    val widthDp: Dp,
    val heightDp: Dp,
) {
    val isCompact get() = widthClass == WindowWidthClass.Compact
    val isMedium get() = widthClass == WindowWidthClass.Medium
    val isExpanded get() = widthClass == WindowWidthClass.Expanded
}

fun calculateWindowSizeClass(widthDp: Dp, heightDp: Dp): WindowSizeClass {
    val widthClass = when {
        widthDp < 600.dp -> WindowWidthClass.Compact
        widthDp < 840.dp -> WindowWidthClass.Medium
        else -> WindowWidthClass.Expanded
    }
    val heightClass = when {
        heightDp < 480.dp -> WindowHeightClass.Compact
        heightDp < 900.dp -> WindowHeightClass.Medium
        else -> WindowHeightClass.Expanded
    }
    return WindowSizeClass(widthClass, heightClass, widthDp, heightDp)
}

/**
 * Select a value based on the current window width class.
 */
@Composable
fun <T> responsiveValue(
    compact: T,
    medium: T = compact,
    expanded: T = medium,
): T {
    val windowSize = LocalWindowSizeClass.current
    return when (windowSize.widthClass) {
        WindowWidthClass.Compact -> compact
        WindowWidthClass.Medium -> medium
        WindowWidthClass.Expanded -> expanded
    }
}

/**
 * Number of grid columns based on window size and content type.
 */
fun WindowSizeClass.posterColumns(isTV: Boolean = false): Int = when {
    isTV -> 7
    isExpanded -> 5
    isMedium -> 4
    else -> 3
}

fun WindowSizeClass.landscapeColumns(isTV: Boolean = false): Int = when {
    isTV -> 4
    isExpanded -> 3
    isMedium -> 3
    else -> 2
}

fun WindowSizeClass.contentPadding(): Dp = when (widthClass) {
    WindowWidthClass.Compact -> 16.dp
    WindowWidthClass.Medium -> 24.dp
    WindowWidthClass.Expanded -> 32.dp
}

val LocalWindowSizeClass = staticCompositionLocalOf {
    WindowSizeClass(
        WindowWidthClass.Compact,
        WindowHeightClass.Medium,
        360.dp,
        640.dp,
    )
}
