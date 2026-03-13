package com.lagradost.cloudstream3.designsystem.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.lagradost.cloudstream3.designsystem.components.*
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.foundation.responsiveValue
import com.lagradost.cloudstream3.designsystem.screens.*
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.AlphaDefaults
import com.lagradost.cloudstream3.designsystem.tokens.CSGradients
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons

/**
 * Detail hero section — full-bleed backdrop with gradient scrim and back button.
 * 35-40% viewport height. Used by both movie and TV show detail screens.
 */
@Composable
fun DetailHero(
    backdropUrl: String?,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val heroHeight = responsiveValue(compact = 260.dp, medium = 320.dp, expanded = 380.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heroHeight),
    ) {
        AsyncImage(
            model = backdropUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Bottom gradient scrim
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter)
                .background(CSGradients.heroBottomScrim),
        )

        // Top fade for status bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopCenter)
                .background(CSGradients.topFade),
        )

        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(CSTheme.spacing.lg)
                .statusBarsPadding(),
        ) {
            Icon(
                imageVector = CSIcons.Back,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(CSTheme.sizes.iconLg),
            )
        }
    }
}

/**
 * Detail title section — title, metadata row, and genre tags.
 */
@Composable
fun DetailTitleSection(
    title: String,
    modifier: Modifier = Modifier,
    year: String? = null,
    rating: String? = null,
    duration: String? = null,
    contentRating: String? = null,
    genres: List<String> = emptyList(),
) {
    Column(
        modifier = modifier.padding(horizontal = CSTheme.spacing.lg),
    ) {
        Text(
            text = title,
            style = CSTheme.typography.headlineMedium,
            color = CSTheme.colors.textPrimary,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(Modifier.height(CSTheme.spacing.xs))

        // Metadata row: year · rating · duration
        val metadata = buildList {
            year?.let { add(it) }
            duration?.let { add(it) }
        }
        if (metadata.isNotEmpty() || contentRating != null || rating != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.sm),
            ) {
                if (contentRating != null) {
                    CSRatingBadge(rating = contentRating)
                }
                if (metadata.isNotEmpty()) {
                    CSMetadataRow(items = metadata)
                }
                if (rating != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.xxs),
                    ) {
                        Icon(
                            imageVector = CSIcons.Star,
                            contentDescription = null,
                            tint = CSTheme.colors.badgeRating,
                            modifier = Modifier.size(CSTheme.sizes.iconSm),
                        )
                        Text(
                            text = rating,
                            style = CSTheme.typography.caption,
                            color = CSTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }

        // Genre tags
        if (genres.isNotEmpty()) {
            Spacer(Modifier.height(CSTheme.spacing.xs))
            Text(
                text = genres.joinToString(" · "),
                style = CSTheme.typography.caption,
                color = CSTheme.colors.textTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Action button row — Play + icon actions (My List, Download, Share, Rate).
 * Netflix-style layout: full-width Play button + horizontal icon row below.
 */
@Composable
fun DetailActionBar(
    modifier: Modifier = Modifier,
    isInWatchlist: Boolean = false,
    onPlayClick: () -> Unit = {},
    onWatchlistClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    val maxButtonWidth = responsiveValue(compact = Dp.Unspecified, medium = 500.dp, expanded = 500.dp)
    val buttonModifier = if (maxButtonWidth != Dp.Unspecified) {
        Modifier.widthIn(max = maxButtonWidth)
    } else {
        Modifier
    }

    Column(
        modifier = modifier.padding(horizontal = CSTheme.spacing.lg),
    ) {
        CSPrimaryButton(
            text = "Play",
            onClick = onPlayClick,
            icon = CSIcons.Play,
            modifier = buttonModifier,
        )

        Spacer(Modifier.height(CSTheme.spacing.xs))

        CSSecondaryButton(
            text = "Download",
            onClick = onDownloadClick,
            icon = CSIcons.Download,
            modifier = buttonModifier.then(Modifier.fillMaxWidth()),
        )

        Spacer(Modifier.height(CSTheme.spacing.sm))

        Row(
            modifier = buttonModifier.then(Modifier.fillMaxWidth()),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CSIconActionButton(
                icon = if (isInWatchlist) CSIcons.Check else CSIcons.Add,
                label = "My List",
                onClick = onWatchlistClick,
            )
            CSIconActionButton(
                icon = CSIcons.ThumbUp,
                label = "Rate",
                onClick = {},
            )
            CSIconActionButton(
                icon = CSIcons.Share,
                label = "Share",
                onClick = onShareClick,
            )
        }
    }
}

/**
 * Cast section — horizontal scrollable row of actor avatars.
 */
@Composable
fun CastSection(
    cast: List<CastMember>,
    modifier: Modifier = Modifier,
) {
    if (cast.isEmpty()) return

    Column(modifier = modifier) {
        CSSectionHeader(title = "Cast & Crew")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = CSTheme.spacing.lg),
            horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.md),
        ) {
            cast.forEach { member ->
                CSAvatar(
                    imageUrl = member.imageUrl,
                    name = member.name,
                    size = CSTheme.sizes.avatarLg,
                )
            }
        }
    }
}

/**
 * Recommendations section — "More Like This" horizontal poster row.
 */
@Composable
fun RecommendationsSection(
    items: List<ContentItem>,
    modifier: Modifier = Modifier,
    onItemClick: (ContentItem) -> Unit = {},
) {
    if (items.isEmpty()) return

    CSContentRow(
        title = "More Like This",
        modifier = modifier,
    ) {
        items.forEach { item ->
            CSPosterCard(
                imageUrl = item.imageUrl,
                title = item.title,
                subtitle = item.subtitle,
                onClick = { onItemClick(item) },
                badge = item.badgeText?.let { text ->
                    { CSBadge(text = text) }
                },
            )
        }
    }
}

/**
 * Trailers section — horizontal row of landscape trailer cards.
 */
@Composable
fun TrailersSection(
    trailers: List<TrailerItem>,
    modifier: Modifier = Modifier,
    onTrailerClick: (TrailerItem) -> Unit = {},
) {
    if (trailers.isEmpty()) return

    CSContentRow(
        title = "Trailers & More",
        modifier = modifier,
    ) {
        trailers.forEach { trailer ->
            CSLandscapeCard(
                imageUrl = trailer.thumbnailUrl,
                title = trailer.name,
                onClick = { onTrailerClick(trailer) },
            )
        }
    }
}

/**
 * Season selector dropdown — pill-shaped button that opens season list.
 */
@Composable
fun SeasonSelector(
    seasons: List<SeasonInfo>,
    selectedIndex: Int,
    onSeasonSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (seasons.isEmpty()) return

    val interactionSource = remember { MutableInteractionSource() }
    val selected = seasons.getOrNull(selectedIndex) ?: return

    Row(
        modifier = modifier
            .padding(horizontal = CSTheme.spacing.lg)
            .background(CSTheme.colors.surfaceTertiary, CSTheme.shapes.pill)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.pill)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    // Cycle to next season for simplicity; real impl would open a bottom sheet
                    val next = (selectedIndex + 1) % seasons.size
                    onSeasonSelected(next)
                },
            )
            .padding(horizontal = CSTheme.spacing.lg, vertical = CSTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selected.name,
            style = CSTheme.typography.titleMedium,
            color = CSTheme.colors.textPrimary,
        )
        Spacer(Modifier.width(CSTheme.spacing.xs))
        Icon(
            imageVector = CSIcons.ExpandMore,
            contentDescription = "Select season",
            tint = CSTheme.colors.textPrimary,
            modifier = Modifier.size(CSTheme.sizes.iconMd),
        )
    }
}

/**
 * Episode list section — vertical list of episode cards.
 */
@Composable
fun EpisodeListSection(
    episodes: List<EpisodeItem>,
    modifier: Modifier = Modifier,
    onEpisodeClick: (EpisodeItem) -> Unit = {},
) {
    Column(modifier = modifier) {
        episodes.forEach { episode ->
            CSEpisodeCard(
                title = episode.title,
                episodeNumber = episode.episodeNumber,
                thumbnailUrl = episode.thumbnailUrl,
                duration = episode.duration,
                description = episode.description,
                progressPercent = episode.progressPercent,
                onClick = { onEpisodeClick(episode) },
                trailing = {
                    Icon(
                        imageVector = CSIcons.Download,
                        contentDescription = "Download",
                        tint = CSTheme.colors.textTertiary,
                        modifier = Modifier.size(CSTheme.sizes.iconLg),
                    )
                },
            )
        }
    }
}
