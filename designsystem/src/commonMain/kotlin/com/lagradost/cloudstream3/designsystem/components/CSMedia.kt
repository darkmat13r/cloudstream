package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.AlphaDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Episode card — horizontal layout with thumbnail left and text right.
 * Netflix-style episode list item with play overlay, progress, and download icon.
 */
@Composable
fun CSEpisodeCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    thumbnailUrl: String? = null,
    thumbnailWidth: Dp = CSTheme.sizes.episodeThumbnailWidth,
    duration: String? = null,
    description: String? = null,
    episodeNumber: String? = null,
    progressPercent: Float? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val thumbnailHeight = thumbnailWidth * 9f / 16f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(
                horizontal = CSTheme.spacing.lg,
                vertical = CSTheme.spacing.sm,
            ),
        verticalAlignment = Alignment.Top,
    ) {
        // Thumbnail with play overlay
        Box(
            modifier = Modifier
                .size(thumbnailWidth, thumbnailHeight)
                .clip(CSTheme.shapes.sm),
        ) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Play icon overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = AlphaDefaults.Overlay)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = CSIcons.Play,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(CSTheme.sizes.iconXxl),
                )
            }

            // Progress bar
            if (progressPercent != null && progressPercent > 0f) {
                CSWatchProgress(
                    progress = progressPercent,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }

        Spacer(Modifier.width(CSTheme.spacing.md))

        // Text content
        Column(modifier = Modifier.weight(1f)) {
            if (episodeNumber != null) {
                Text(
                    text = episodeNumber,
                    style = CSTheme.typography.overline,
                    color = CSTheme.colors.textTertiary,
                )
                Spacer(Modifier.height(CSTheme.spacing.xxs))
            }
            Text(
                text = title,
                style = CSTheme.typography.titleLarge,
                color = CSTheme.colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (duration != null) {
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                Text(
                    text = duration,
                    style = CSTheme.typography.caption,
                    color = CSTheme.colors.textTertiary,
                )
            }
            if (description != null) {
                Spacer(Modifier.height(CSTheme.spacing.xs))
                Text(
                    text = description,
                    style = CSTheme.typography.bodySmall,
                    color = CSTheme.colors.textSecondary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (trailing != null) {
            Spacer(Modifier.width(CSTheme.spacing.sm))
            trailing()
        }
    }
}

/**
 * Playback progress bar — used in video player with elapsed/remaining time.
 */
@Composable
fun CSPlaybackProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    bufferedProgress: Float = 0f,
    elapsedTime: String? = null,
    remainingTime: String? = null,
    trackColor: Color = CSTheme.colors.playerTrack,
    progressColor: Color = CSTheme.colors.playerProgress,
    bufferColor: Color = CSTheme.colors.playerBuffer,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CSTheme.sizes.progressBarHeight)
                .clip(CSTheme.shapes.xs),
        ) {
            // Track
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(trackColor),
            )
            // Buffer
            if (bufferedProgress > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(bufferedProgress.coerceIn(0f, 1f))
                        .background(bufferColor),
                )
            }
            // Progress
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .background(progressColor),
            )
        }

        if (elapsedTime != null || remainingTime != null) {
            Spacer(Modifier.height(CSTheme.spacing.xxs))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = elapsedTime ?: "",
                    style = CSTheme.typography.caption,
                    color = CSTheme.colors.textPrimary,
                )
                Text(
                    text = remainingTime ?: "",
                    style = CSTheme.typography.caption,
                    color = CSTheme.colors.textTertiary,
                )
            }
        }
    }
}

/**
 * Metadata row — year, rating, duration displayed inline.
 * Used below content titles in detail screens.
 */
@Composable
fun CSMetadataRow(
    items: List<String>,
    modifier: Modifier = Modifier,
    separator: String = " · ",
) {
    Text(
        text = items.joinToString(separator),
        style = CSTheme.typography.caption,
        color = CSTheme.colors.textTertiary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

/**
 * Expandable synopsis/description text.
 * Shows limited lines with a "MORE" expand link.
 */
@Composable
fun CSSynopsis(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 3,
    expanded: Boolean = false,
    onToggle: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Text(
            text = text,
            style = CSTheme.typography.bodyMedium,
            color = CSTheme.colors.textSecondary,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
        )
        if (onToggle != null) {
            Spacer(Modifier.height(CSTheme.spacing.xxs))
            Text(
                text = if (expanded) "LESS" else "MORE",
                style = CSTheme.typography.titleSmall,
                color = CSTheme.colors.textTertiary,
                modifier = Modifier.clickable(onClick = onToggle),
            )
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSEpisodeCardPreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            CSEpisodeCard(
                title = "Pilot",
                episodeNumber = "S1 · E1",
                duration = "58 min",
                description = "A chemistry teacher diagnosed with terminal lung cancer teams up with a former student...",
                onClick = {},
                progressPercent = 0.4f,
            )
            CSEpisodeCard(
                title = "Cat's in the Bag...",
                episodeNumber = "S1 · E2",
                duration = "48 min",
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun CSPlaybackProgressBarPreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CSPlaybackProgressBar(
                progress = 0.35f,
                bufferedProgress = 0.65f,
                elapsedTime = "12:45",
                remainingTime = "-23:15",
            )
        }
    }
}

@Preview
@Composable
private fun CSSynopsisPreview() {
    CSTheme {
        CSSynopsis(
            text = "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.",
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            onToggle = {},
        )
    }
}
