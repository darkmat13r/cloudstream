package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween

/**
 * Animation specs derived from streaming platform motion patterns.
 * Netflix/HBO Max use ease-out for entries, ease-in for exits.
 */
object CSAnimation {
    // Durations (ms)
    const val Instant = 0
    const val Fast = 120
    const val Normal = 200
    const val Emphasized = 300
    const val Sheet = 280
    const val SheetDismiss = 180
    const val Carousel = 250
    const val FocusScale = 150
    const val TabSwitch = 160
    const val MicroInteraction = 160
    const val FadeIn = 220
    const val FadeOut = 150

    // Easing
    val EaseOut = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
    val EaseIn = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
    val EaseInOut = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
    val Decelerate = CubicBezierEasing(0.0f, 0.0f, 0.05f, 1.0f)
    val EmphasizedEasing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)

    // Pre-built tween specs
    fun <T> fastEaseOut() = tween<T>(durationMillis = Fast, easing = EaseOut)
    fun <T> normalEaseOut() = tween<T>(durationMillis = Normal, easing = EaseOut)
    fun <T> emphasizedEaseOut() = tween<T>(durationMillis = Emphasized, easing = EaseOut)
    fun <T> sheetEntry() = tween<T>(durationMillis = Sheet, easing = EaseOut)
    fun <T> sheetExit() = tween<T>(durationMillis = SheetDismiss, easing = EaseIn)
    fun <T> focusScale() = tween<T>(durationMillis = FocusScale, easing = EaseOut)
    fun <T> carouselSnap() = tween<T>(durationMillis = Carousel, easing = Decelerate)

    // Scale values
    const val PressedScale = 0.97f
    const val FocusedScale = 1.05f
    const val TVFocusedScale = 1.08f
    const val DefaultScale = 1.0f
}
