package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.foundation.responsiveValue
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.CSGradients
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Poster card — 2:3 aspect ratio content thumbnail.
 * Used in content rows, grids, "More Like This" sections.
 * Supports TV focus with scale animation.
 */
@Composable
fun CSPosterCard(
    imageUrl: String?,
    title: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    width: Dp = responsiveValue(
        compact = CSTheme.sizes.posterWidthMd,
        medium = CSTheme.sizes.posterWidthLg,
        expanded = if (CSTheme.isTV) CSTheme.sizes.tvPosterWidth else CSTheme.sizes.posterWidthLg,
    ),
    progressPercent: Float? = null,
    badge: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val height = width * 1.5f // 2:3 ratio

    Column(
        modifier = modifier.width(width),
    ) {
        Box(
            modifier = Modifier
                .size(width, height)
                .clip(CSTheme.shapes.xs)
                .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.xs)
                .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Badge overlay (top-left)
            if (badge != null) {
                Box(modifier = Modifier.align(Alignment.TopStart).padding(CSTheme.spacing.xs)) {
                    badge()
                }
            }

            // Progress bar overlay (bottom)
            if (progressPercent != null && progressPercent > 0f) {
                CSWatchProgress(
                    progress = progressPercent,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }

        if (title != null) {
            Spacer(Modifier.height(CSTheme.spacing.xs))
            Text(
                text = title,
                style = CSTheme.typography.bodySmall,
                color = CSTheme.colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = CSTheme.typography.caption,
                color = CSTheme.colors.textTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Landscape card — 16:9 aspect ratio for episodes, search results.
 */
@Composable
fun CSLandscapeCard(
    imageUrl: String?,
    title: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    width: Dp = responsiveValue(
        compact = CSTheme.sizes.landscapeWidthMd,
        medium = CSTheme.sizes.landscapeWidthLg,
        expanded = if (CSTheme.isTV) CSTheme.sizes.tvLandscapeWidth else CSTheme.sizes.landscapeWidthLg,
    ),
    progressPercent: Float? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val height = width * 9f / 16f

    Column(modifier = modifier.width(width)) {
        Box(
            modifier = Modifier
                .size(width, height)
                .clip(CSTheme.shapes.sm)
                .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.sm)
                .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            if (progressPercent != null && progressPercent > 0f) {
                CSWatchProgress(
                    progress = progressPercent,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }

        if (title != null) {
            Spacer(Modifier.height(CSTheme.spacing.xs))
            Text(
                text = title,
                style = CSTheme.typography.bodySmall,
                color = CSTheme.colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = CSTheme.typography.caption,
                color = CSTheme.colors.textTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Hero card — full-width featured content with gradient overlay.
 * Occupies 35-40% of viewport height on mobile, less on TV.
 */
@Composable
fun CSHeroCard(
    imageUrl: String?,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    metadata: String? = null,
    actionContent: @Composable (RowScope.() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val heroHeight = responsiveValue(compact = 280.dp, medium = 340.dp, expanded = 400.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heroHeight)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.none)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
    ) {
        // Background image
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Bottom gradient scrim
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .background(CSGradients.heroBottomScrim),
        )

        // Content overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = CSTheme.spacing.lg, vertical = CSTheme.spacing.xl),
        ) {
            Text(
                text = title,
                style = CSTheme.typography.displaySmall,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (subtitle != null) {
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                Text(
                    text = subtitle,
                    style = CSTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.87f),
                    maxLines = 1,
                )
            }
            if (metadata != null) {
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                Text(
                    text = metadata,
                    style = CSTheme.typography.caption,
                    color = Color.White.copy(alpha = 0.7f),
                )
            }
            if (actionContent != null) {
                Spacer(Modifier.height(CSTheme.spacing.md))
                Row(
                    modifier = Modifier.widthIn(max = 400.dp),
                    horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.sm),
                    content = actionContent,
                )
            }
        }
    }
}

/**
 * Thin progress bar at the bottom of poster/landscape cards.
 * Netflix-style "continue watching" indicator.
 */
@Composable
fun CSWatchProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = CSTheme.colors.playerTrack,
    progressColor: Color = CSTheme.colors.playerProgress,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.progressBarHeight)
            .background(trackColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .background(progressColor),
        )
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSPosterCardPreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSPosterCard(
                imageUrl = null,
                title = "Stranger Things",
                subtitle = "Season 5",
                onClick = {},
                width = 120.dp,
                progressPercent = 0.6f,
            )
            CSPosterCard(
                imageUrl = null,
                title = "The Bear",
                onClick = {},
                width = 120.dp,
                badge = { CSBadge(text = "NEW") },
            )
        }
    }
}

@Preview
@Composable
private fun CSLandscapeCardPreview() {
    CSTheme {
        CSLandscapeCard(
            imageUrl = null,
            title = "Breaking Bad - S1 E1",
            subtitle = "50 min",
            onClick = {},
            width = 200.dp,
            progressPercent = 0.3f,
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun CSHeroCardPreview() {
    CSTheme {
        CSHeroCard(
            imageUrl = null,
            title = "House of the Dragon",
            subtitle = "HBO Original",
            metadata = "Drama · Fantasy · 2024",
            onClick = {},
        )
    }
}
