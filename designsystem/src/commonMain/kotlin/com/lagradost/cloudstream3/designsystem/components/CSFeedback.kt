package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Badge — small status indicator.
 * Used for "NEW", "TOP 10", maturity ratings, quality badges.
 */
@Composable
fun CSBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = CSTheme.colors.badgeNew,
    contentColor: Color = CSTheme.colors.textOnAccent,
) {
    Text(
        text = text,
        style = CSTheme.typography.overline,
        color = contentColor,
        modifier = modifier
            .background(containerColor, CSTheme.shapes.xs)
            .padding(horizontal = CSTheme.spacing.xs, vertical = CSTheme.spacing.xxs),
    )
}

/**
 * Rating badge — yellow pill for maturity ratings ("12+", "TV-MA").
 */
@Composable
fun CSRatingBadge(
    rating: String,
    modifier: Modifier = Modifier,
) {
    CSBadge(
        text = rating,
        modifier = modifier,
        containerColor = CSTheme.colors.badgeRating,
        contentColor = CSTheme.colors.textOnAccent,
    )
}

/**
 * Top 10 badge — red numbered badge for trending content.
 */
@Composable
fun CSTop10Badge(
    rank: Int,
    modifier: Modifier = Modifier,
) {
    CSBadge(
        text = "TOP $rank",
        modifier = modifier,
        containerColor = CSTheme.colors.badgeTop10,
        contentColor = Color.White,
    )
}

/**
 * Profile avatar — circular image with optional selection ring.
 * Used in profile selection grids and nav bar.
 */
@Composable
fun CSAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = CSTheme.sizes.avatarLg,
    name: String? = null,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .then(
                    if (selected) Modifier.background(
                        color = CSTheme.colors.borderFocus,
                        shape = CircleShape,
                    ).padding(3.dp) else Modifier
                )
                .clip(CircleShape)
                .background(CSTheme.colors.surfaceTertiary, CircleShape)
                .then(
                    if (onClick != null) Modifier
                        .csFocusable(interactionSource = interactionSource, shape = CircleShape)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                        ) else Modifier
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                )
            } else if (name != null) {
                Text(
                    text = name.take(1).uppercase(),
                    style = CSTheme.typography.headlineMedium,
                    color = CSTheme.colors.textPrimary,
                )
            }
        }
        if (name != null) {
            Spacer(Modifier.height(CSTheme.spacing.xs))
            Text(
                text = name,
                style = CSTheme.typography.caption,
                color = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(max = size + 16.dp),
            )
        }
    }
}

/**
 * Shimmer placeholder — skeleton loading animation.
 * Netflix-style gray rectangles with shimmer sweep.
 */
@Composable
fun CSShimmer(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -300f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_translate",
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            CSTheme.colors.shimmerBase,
            CSTheme.colors.shimmerHighlight,
            CSTheme.colors.shimmerBase,
        ),
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 300f, 0f),
    )

    Box(modifier = modifier.background(shimmerBrush))
}

/**
 * Shimmer poster card placeholder — matches [CSPosterCard] dimensions.
 */
@Composable
fun CSShimmerPosterCard(
    modifier: Modifier = Modifier,
    width: Dp = CSTheme.sizes.posterWidthMd,
) {
    val height = width * 1.5f

    Column(modifier = modifier.width(width)) {
        CSShimmer(
            modifier = Modifier
                .size(width, height)
                .clip(CSTheme.shapes.xs),
        )
        Spacer(Modifier.height(CSTheme.spacing.xs))
        CSShimmer(
            modifier = Modifier
                .width(width * 0.8f)
                .height(14.dp)
                .clip(CSTheme.shapes.xs),
        )
        Spacer(Modifier.height(CSTheme.spacing.xxs))
        CSShimmer(
            modifier = Modifier
                .width(width * 0.5f)
                .height(12.dp)
                .clip(CSTheme.shapes.xs),
        )
    }
}

/**
 * Shimmer landscape card placeholder.
 */
@Composable
fun CSShimmerLandscapeCard(
    modifier: Modifier = Modifier,
    width: Dp = CSTheme.sizes.landscapeWidthMd,
) {
    val height = width * 9f / 16f

    Column(modifier = modifier.width(width)) {
        CSShimmer(
            modifier = Modifier
                .size(width, height)
                .clip(CSTheme.shapes.sm),
        )
        Spacer(Modifier.height(CSTheme.spacing.xs))
        CSShimmer(
            modifier = Modifier
                .width(width * 0.7f)
                .height(14.dp)
                .clip(CSTheme.shapes.xs),
        )
    }
}

/**
 * Shimmer episode card placeholder.
 */
@Composable
fun CSShimmerEpisodeCard(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CSTheme.spacing.lg, vertical = CSTheme.spacing.sm),
    ) {
        CSShimmer(
            modifier = Modifier
                .size(CSTheme.sizes.episodeThumbnailWidth, CSTheme.sizes.episodeThumbnailWidth * 9f / 16f)
                .clip(CSTheme.shapes.sm),
        )
        Spacer(Modifier.width(CSTheme.spacing.md))
        Column(modifier = Modifier.weight(1f)) {
            CSShimmer(
                modifier = Modifier.fillMaxWidth(0.6f).height(14.dp).clip(CSTheme.shapes.xs),
            )
            Spacer(Modifier.height(CSTheme.spacing.sm))
            CSShimmer(
                modifier = Modifier.fillMaxWidth(0.3f).height(12.dp).clip(CSTheme.shapes.xs),
            )
            Spacer(Modifier.height(CSTheme.spacing.sm))
            CSShimmer(
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(CSTheme.shapes.xs),
            )
            Spacer(Modifier.height(CSTheme.spacing.xxs))
            CSShimmer(
                modifier = Modifier.fillMaxWidth(0.8f).height(12.dp).clip(CSTheme.shapes.xs),
            )
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSBadgePreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSBadge(text = "NEW")
            CSRatingBadge(rating = "TV-MA")
            CSTop10Badge(rank = 3)
            CSBadge(text = "4K", containerColor = CSTheme.colors.surfaceTertiary, contentColor = CSTheme.colors.textPrimary)
        }
    }
}

@Preview
@Composable
private fun CSAvatarPreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CSAvatar(imageUrl = null, name = "Alice", selected = true, onClick = {})
            CSAvatar(imageUrl = null, name = "Bob", onClick = {})
            CSAvatar(imageUrl = null, name = "Kids", size = CSTheme.sizes.avatarMd, onClick = {})
        }
    }
}

@Preview
@Composable
private fun CSShimmerPreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSShimmerPosterCard(width = 100.dp)
            CSShimmerPosterCard(width = 100.dp)
            CSShimmerPosterCard(width = 100.dp)
        }
    }
}

@Preview
@Composable
private fun CSShimmerEpisodePreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .fillMaxWidth(),
        ) {
            CSShimmerEpisodeCard()
            CSShimmerEpisodeCard()
        }
    }
}
