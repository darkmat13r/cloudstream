package com.lagradost.cloudstream3.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.lagradost.cloudstream3.designsystem.foundation.CSFocusConfig
import com.lagradost.cloudstream3.designsystem.foundation.LocalCSFocusConfig
import com.lagradost.cloudstream3.designsystem.foundation.LocalWindowSizeClass
import com.lagradost.cloudstream3.designsystem.foundation.WindowSizeClass
import com.lagradost.cloudstream3.designsystem.foundation.calculateWindowSizeClass
import com.lagradost.cloudstream3.designsystem.tokens.*
import androidx.compose.ui.unit.dp

val LocalIsTV = staticCompositionLocalOf { false }

/**
 * CloudStream Design System theme.
 *
 * Provides design tokens via CompositionLocals and wraps MaterialTheme
 * for interop with Material3 components.
 *
 * @param darkTheme Whether to use dark color scheme (default: system setting)
 * @param isTV Whether the device is a TV (enables focus ring + d-pad navigation)
 * @param windowSizeClass Current window size class for responsive layouts
 * @param colors Custom color scheme override
 * @param typography Custom typography override
 * @param spacing Custom spacing override
 * @param sizes Custom sizes override
 * @param shapes Custom shapes override
 */
@Composable
fun CSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isTV: Boolean = false,
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(360.dp, 640.dp),
    colors: CSColorScheme = if (darkTheme) CSDarkColors else CSLightColors,
    typography: CSTypographyScale = CSDefaultTypography,
    spacing: CSSpacing = CSSpacing(),
    sizes: CSSizes = CSSizes(),
    shapes: CSShapeScale = CSShapeScale(),
    elevation: CSElevation = CSElevation(),
    content: @Composable () -> Unit,
) {
    val focusConfig = CSFocusConfig(
        isTV = isTV,
        focusRingColor = colors.borderFocus,
    )

    // Bridge to Material3 for interop
    val materialColors = if (darkTheme) {
        darkColorScheme(
            primary = colors.accentPrimary,
            onPrimary = colors.textOnAccent,
            secondary = colors.accentSecondary,
            background = colors.background,
            surface = colors.surface,
            onBackground = colors.textPrimary,
            onSurface = colors.textPrimary,
            error = colors.accentError,
            outline = colors.borderCard,
        )
    } else {
        lightColorScheme(
            primary = colors.accentPrimary,
            onPrimary = colors.textOnAccent,
            secondary = colors.accentSecondary,
            background = colors.background,
            surface = colors.surface,
            onBackground = colors.textPrimary,
            onSurface = colors.textPrimary,
            error = colors.accentError,
            outline = colors.borderCard,
        )
    }

    CompositionLocalProvider(
        LocalCSColors provides colors,
        LocalCSTypography provides typography,
        LocalCSSpacing provides spacing,
        LocalCSSizes provides sizes,
        LocalCSShapes provides shapes,
        LocalCSElevation provides elevation,
        LocalWindowSizeClass provides windowSizeClass,
        LocalCSFocusConfig provides focusConfig,
        LocalIsTV provides isTV,
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            content = content,
        )
    }
}

/**
 * Accessor object for design tokens within a [CSTheme] scope.
 *
 * Usage:
 * ```
 * Text(
 *     text = "Hello",
 *     color = CSTheme.colors.textPrimary,
 *     style = CSTheme.typography.headlineMedium,
 * )
 * ```
 */
object CSTheme {
    val colors: CSColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalCSColors.current

    val typography: CSTypographyScale
        @Composable @ReadOnlyComposable
        get() = LocalCSTypography.current

    val spacing: CSSpacing
        @Composable @ReadOnlyComposable
        get() = LocalCSSpacing.current

    val sizes: CSSizes
        @Composable @ReadOnlyComposable
        get() = LocalCSSizes.current

    val shapes: CSShapeScale
        @Composable @ReadOnlyComposable
        get() = LocalCSShapes.current

    val elevation: CSElevation
        @Composable @ReadOnlyComposable
        get() = LocalCSElevation.current

    val windowSize: WindowSizeClass
        @Composable @ReadOnlyComposable
        get() = LocalWindowSizeClass.current

    val isTV: Boolean
        @Composable @ReadOnlyComposable
        get() = LocalIsTV.current

    val focusConfig: CSFocusConfig
        @Composable @ReadOnlyComposable
        get() = LocalCSFocusConfig.current
}
