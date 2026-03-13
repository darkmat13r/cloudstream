package com.lagradost.cloudstream3.designsystem.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.*
import com.lagradost.cloudstream3.designsystem.foundation.responsiveValue
import com.lagradost.cloudstream3.designsystem.screens.*
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Movie detail screen.
 *
 * Layout (top to bottom):
 * 1. Hero backdrop with back button and gradient scrim
 * 2. Title + metadata (year, rating, duration, genres)
 * 3. Action buttons (Play, Download, My List, Rate, Share)
 * 4. Synopsis (expandable)
 * 5. Cast section (horizontal avatar row)
 * 6. Trailers section
 * 7. More Like This (recommendations row)
 */
@Composable
fun MovieDetailScreen(
    state: MovieDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onRecommendationClick: (ContentItem) -> Unit = {},
    onTrailerClick: (TrailerItem) -> Unit = {},
    onWatchlistClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    val detail = state.detail
    var synopsisExpanded by remember { mutableStateOf(false) }
    val contentMaxWidth = responsiveValue(
        compact = Dp.Unspecified,
        medium = 640.dp,
        expanded = 720.dp,
    )
    val contentModifier = if (contentMaxWidth != Dp.Unspecified) {
        Modifier.widthIn(max = contentMaxWidth)
    } else {
        Modifier
    }

    if (detail.isLoading) {
        MovieDetailShimmer(modifier = modifier)
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CSTheme.colors.background),
    ) {
        // 1. Hero
        item(key = "hero") {
            DetailHero(
                backdropUrl = detail.backdropUrl,
                onBackClick = onBackClick,
            )
        }

        // 2. Title + metadata
        item(key = "title") {
            Spacer(Modifier.height(CSTheme.spacing.lg))
            DetailTitleSection(
                title = detail.title,
                year = detail.year,
                rating = detail.rating,
                duration = detail.duration,
                contentRating = detail.contentRating,
                genres = detail.genres,
                modifier = contentModifier,
            )
        }

        // 3. Action buttons
        item(key = "actions") {
            Spacer(Modifier.height(CSTheme.spacing.lg))
            DetailActionBar(
                isInWatchlist = detail.isInWatchlist,
                onPlayClick = onPlayClick,
                onWatchlistClick = onWatchlistClick,
                onDownloadClick = onDownloadClick,
                onShareClick = onShareClick,
                modifier = contentModifier,
            )
        }

        // 4. Synopsis
        if (detail.synopsis != null) {
            item(key = "synopsis") {
                Spacer(Modifier.height(CSTheme.spacing.lg))
                CSSynopsis(
                    text = detail.synopsis,
                    expanded = synopsisExpanded,
                    onToggle = { synopsisExpanded = !synopsisExpanded },
                    modifier = contentModifier.then(
                        Modifier.padding(horizontal = CSTheme.spacing.lg),
                    ),
                )
            }
        }

        // 5. Cast
        if (detail.cast.isNotEmpty()) {
            item(key = "cast") {
                Spacer(Modifier.height(CSTheme.spacing.xl))
                CastSection(
                    cast = detail.cast,
                    modifier = contentModifier,
                )
            }
        }

        // 6. Trailers
        if (detail.trailers.isNotEmpty()) {
            item(key = "trailers") {
                Spacer(Modifier.height(CSTheme.spacing.xl))
                TrailersSection(
                    trailers = detail.trailers,
                    onTrailerClick = onTrailerClick,
                )
            }
        }

        // 7. More Like This
        if (detail.recommendations.isNotEmpty()) {
            item(key = "recommendations") {
                Spacer(Modifier.height(CSTheme.spacing.xl))
                RecommendationsSection(
                    items = detail.recommendations,
                    onItemClick = onRecommendationClick,
                )
            }
        }

        item { Spacer(Modifier.height(CSTheme.spacing.xxxxl)) }
    }
}

@Composable
private fun MovieDetailShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CSTheme.colors.background),
    ) {
        item {
            // Hero shimmer
            CSShimmer(
                modifier = Modifier.fillMaxWidth().height(260.dp),
            )
            Spacer(Modifier.height(CSTheme.spacing.lg))

            // Title shimmer
            Column(modifier = Modifier.padding(horizontal = CSTheme.spacing.lg)) {
                CSShimmer(
                    modifier = Modifier.fillMaxWidth(0.7f).height(24.dp),
                )
                Spacer(Modifier.height(CSTheme.spacing.sm))
                CSShimmer(
                    modifier = Modifier.fillMaxWidth(0.4f).height(14.dp),
                )
                Spacer(Modifier.height(CSTheme.spacing.sm))
                CSShimmer(
                    modifier = Modifier.fillMaxWidth(0.3f).height(12.dp),
                )
            }

            // Button shimmer
            Spacer(Modifier.height(CSTheme.spacing.xl))
            Column(modifier = Modifier.padding(horizontal = CSTheme.spacing.lg)) {
                CSShimmer(
                    modifier = Modifier.fillMaxWidth().height(CSTheme.sizes.buttonHeight),
                )
                Spacer(Modifier.height(CSTheme.spacing.sm))
                CSShimmer(
                    modifier = Modifier.fillMaxWidth().height(CSTheme.sizes.buttonHeight),
                )
            }

            // Synopsis shimmer
            Spacer(Modifier.height(CSTheme.spacing.xl))
            Column(modifier = Modifier.padding(horizontal = CSTheme.spacing.lg)) {
                CSShimmer(modifier = Modifier.fillMaxWidth().height(14.dp))
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                CSShimmer(modifier = Modifier.fillMaxWidth().height(14.dp))
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                CSShimmer(modifier = Modifier.fillMaxWidth(0.6f).height(14.dp))
            }

            // Cast shimmer
            Spacer(Modifier.height(CSTheme.spacing.xl))
            Row(
                modifier = Modifier.padding(horizontal = CSTheme.spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.md),
            ) {
                repeat(5) {
                    CSShimmer(
                        modifier = Modifier
                            .size(CSTheme.sizes.avatarLg),
                    )
                }
            }

            // Poster row shimmer
            Spacer(Modifier.height(CSTheme.spacing.xl))
            Row(
                modifier = Modifier.padding(horizontal = CSTheme.spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.sm),
            ) {
                repeat(4) {
                    CSShimmerPosterCard(width = 100.dp)
                }
            }
        }
    }
}

// --- Preview data ---

internal val previewMovieState = MovieDetailUiState(
    detail = DetailUiState(
        title = "Oppenheimer",
        year = "2023",
        rating = "8.4",
        duration = "3h 0m",
        contentRating = "R",
        genres = listOf("Biography", "Drama", "History"),
        synopsis = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II. A sprawling epic that charts the rise and fall of the father of the atomic bomb.",
        cast = listOf(
            CastMember(id = "1", name = "Cillian Murphy", role = "J. Robert Oppenheimer"),
            CastMember(id = "2", name = "Emily Blunt", role = "Kitty Oppenheimer"),
            CastMember(id = "3", name = "Matt Damon", role = "Leslie Groves"),
            CastMember(id = "4", name = "Robert Downey Jr.", role = "Lewis Strauss"),
            CastMember(id = "5", name = "Florence Pugh", role = "Jean Tatlock"),
            CastMember(id = "6", name = "Josh Hartnett", role = "Ernest Lawrence"),
        ),
        recommendations = listOf(
            ContentItem(id = "rec_1", title = "Interstellar", subtitle = "2014"),
            ContentItem(id = "rec_2", title = "Dunkirk", subtitle = "2017"),
            ContentItem(id = "rec_3", title = "The Imitation Game", subtitle = "2014"),
            ContentItem(id = "rec_4", title = "A Beautiful Mind", subtitle = "2001"),
            ContentItem(id = "rec_5", title = "Schindler's List", subtitle = "1993"),
            ContentItem(id = "rec_6", title = "The Prestige", subtitle = "2006"),
            ContentItem(id = "rec_7", title = "Tenet", subtitle = "2020"),
            ContentItem(id = "rec_8", title = "Inception", subtitle = "2010"),
            ContentItem(id = "rec_9", title = "First Man", subtitle = "2018"),
            ContentItem(id = "rec_10", title = "Hidden Figures", subtitle = "2016"),
        ),
        trailers = listOf(
            TrailerItem(id = "t1", name = "Official Trailer"),
            TrailerItem(id = "t2", name = "Behind The Scenes"),
            TrailerItem(id = "t3", name = "Cast Interview"),
        ),
    ),
)

@Preview
@Composable
private fun MovieDetailScreenPreview() {
    CSTheme {
        MovieDetailScreen(state = previewMovieState)
    }
}

@Preview
@Composable
private fun MovieDetailLoadingPreview() {
    CSTheme {
        MovieDetailScreen(
            state = MovieDetailUiState(detail = DetailUiState(isLoading = true)),
        )
    }
}
