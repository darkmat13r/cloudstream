package com.lagradost.cloudstream3.designsystem.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.tokens.CSAnimation

/**
 * Focus state for TV/d-pad navigation. Tracks whether an item is
 * focused, pressed, or selected to render appropriate visual feedback.
 */
@Immutable
data class CSFocusState(
    val isFocused: Boolean = false,
    val isPressed: Boolean = false,
    val isSelected: Boolean = false,
) {
    val isHighlighted get() = isFocused || isPressed
}

/**
 * Configuration for focus visual feedback on TV.
 */
@Immutable
data class CSFocusConfig(
    val isTV: Boolean = false,
    val focusRingColor: Color = Color.White,
    val focusRingWidth: Dp = 3.dp,
    val focusedScale: Float = CSAnimation.FocusedScale,
    val pressedScale: Float = CSAnimation.PressedScale,
    val tvFocusedScale: Float = CSAnimation.TVFocusedScale,
)

val LocalCSFocusConfig = staticCompositionLocalOf { CSFocusConfig() }

/**
 * Modifier that adds TV-compatible focus behavior:
 * - Scale animation on focus (larger on TV)
 * - Focus ring border when focused
 * - Press scale-down animation
 */
@Composable
fun Modifier.csFocusable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape? = null,
    enabled: Boolean = true,
    onFocusChanged: ((Boolean) -> Unit)? = null,
): Modifier {
    val config = LocalCSFocusConfig.current
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val targetScale = when {
        isPressed -> config.pressedScale
        isFocused && config.isTV -> config.tvFocusedScale
        isFocused -> config.focusedScale
        else -> CSAnimation.DefaultScale
    }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = CSAnimation.focusScale(),
        label = "focus_scale",
    )

    return this
        .scale(scale)
        .then(
            if (isFocused && shape != null) {
                Modifier.border(
                    width = if (config.isTV) config.focusRingWidth + 1.dp else config.focusRingWidth,
                    color = config.focusRingColor,
                    shape = shape,
                )
            } else {
                Modifier
            }
        )
        .onFocusChanged { state ->
            onFocusChanged?.invoke(state.isFocused)
        }
        .focusable(enabled = enabled, interactionSource = interactionSource)
}

/**
 * Modifier that adds scale-on-focus without a focus ring.
 * Used for cards in carousels where the scale alone indicates focus.
 */
@Composable
fun Modifier.csScaleOnFocus(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
): Modifier {
    val config = LocalCSFocusConfig.current
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val targetScale = when {
        isPressed -> config.pressedScale
        isFocused && config.isTV -> config.tvFocusedScale
        isFocused -> config.focusedScale
        else -> CSAnimation.DefaultScale
    }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = CSAnimation.focusScale(),
        label = "scale_on_focus",
    )

    return this
        .scale(scale)
        .focusable(enabled = enabled, interactionSource = interactionSource)
}
