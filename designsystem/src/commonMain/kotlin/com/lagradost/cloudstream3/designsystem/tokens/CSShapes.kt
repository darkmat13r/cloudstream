package com.lagradost.cloudstream3.designsystem.tokens

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * CloudStream shape tokens.
 * Corner radii: 4px thumbnails → 8px cards → 12px modals → full-round pills.
 */
@Immutable
data class CSShapeScale(
    /** 0dp — Full-bleed hero cards */
    val none: Shape = RoundedCornerShape(0.dp),
    /** 4dp — Content poster thumbnails */
    val xs: Shape = RoundedCornerShape(4.dp),
    /** 6dp — Episode thumbnails */
    val sm: Shape = RoundedCornerShape(6.dp),
    /** 8dp — Cards, search fields, category tiles */
    val md: Shape = RoundedCornerShape(8.dp),
    /** 12dp — Bottom sheets (top corners), modals */
    val lg: Shape = RoundedCornerShape(12.dp),
    /** 16dp — Subscription cards, large modals */
    val xl: Shape = RoundedCornerShape(16.dp),
    /** 20dp — Extra-large panels */
    val xxl: Shape = RoundedCornerShape(20.dp),
    /** Full circle — Pills, chips, avatars */
    val pill: Shape = CircleShape,
    /** Top-only 12dp — Bottom sheets */
    val bottomSheet: Shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    /** Top-only 16dp — Large bottom sheets */
    val bottomSheetLarge: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
)

val LocalCSShapes = staticCompositionLocalOf { CSShapeScale() }
