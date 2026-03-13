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
 * TV show detail screen.
 *
 * Layout (top to bottom):
 * 1. Hero backdrop with back button and gradient scrim
 * 2. Title + metadata (year, rating, duration, genres)
 * 3. Action buttons (Play, Download, My List, Rate, Share)
 * 4. Synopsis (expandable)
 * 5. Tab row: Episodes | More Like This | Trailers
 * 6. Season selector
 * 7. Episode list
 * 8. Recommendations grid
 */
@Composable
fun TvShowDetailScreen(
    state: TvShowDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onEpisodeClick: (EpisodeItem) -> Unit = {},
    onRecommendationClick: (ContentItem) -> Unit = {},
    onSeasonSelected: (Int) -> Unit = {},
    onWatchlistClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    val detail = state.detail
    var synopsisExpanded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Episodes", "More Like This", "Trailers & More")
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
        TvShowDetailShimmer(modifier = modifier)
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

        // 5. Tab row
        item(key = "tabs") {
            Spacer(Modifier.height(CSTheme.spacing.xl))
            CSCategoryTabs(
                tabs = tabs,
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = contentModifier,
            )
        }

        // Tab content
        when (selectedTab) {
            // Episodes tab
            0 -> {
                // 6. Season selector
                if (state.seasons.size > 1) {
                    item(key = "season_selector") {
                        Spacer(Modifier.height(CSTheme.spacing.md))
                        SeasonSelector(
                            seasons = state.seasons,
                            selectedIndex = state.selectedSeasonIndex,
                            onSeasonSelected = onSeasonSelected,
                            modifier = contentModifier,
                        )
                    }
                }

                // 7. Episode list
                item(key = "episodes") {
                    Spacer(Modifier.height(CSTheme.spacing.sm))
                    EpisodeListSection(
                        episodes = state.episodes,
                        onEpisodeClick = onEpisodeClick,
                        modifier = contentModifier,
                    )
                }
            }

            // More Like This tab
            1 -> {
                item(key = "recommendations") {
                    Spacer(Modifier.height(CSTheme.spacing.md))
                    RecommendationsSection(
                        items = detail.recommendations,
                        onItemClick = onRecommendationClick,
                    )
                }
            }

            // Trailers tab
            2 -> {
                item(key = "trailers") {
                    Spacer(Modifier.height(CSTheme.spacing.md))
                    TrailersSection(
                        trailers = detail.trailers,
                    )
                }
            }
        }

        // Cast
        if (detail.cast.isNotEmpty()) {
            item(key = "cast") {
                Spacer(Modifier.height(CSTheme.spacing.xl))
                CastSection(
                    cast = detail.cast,
                    modifier = contentModifier,
                )
            }
        }

        item { Spacer(Modifier.height(CSTheme.spacing.xxxxl)) }
    }
}

@Composable
private fun TvShowDetailShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CSTheme.colors.background),
    ) {
        item {
            CSShimmer(
                modifier = Modifier.fillMaxWidth().height(CSTheme.sizes.topBarHeight * 5),
            )
            Spacer(Modifier.height(CSTheme.spacing.lg))
            Column(modifier = Modifier.padding(horizontal = CSTheme.spacing.lg)) {
                CSShimmer(
                    modifier = Modifier.fillMaxWidth(0.6f).height(24.dp),
                )
                Spacer(Modifier.height(CSTheme.spacing.sm))
                CSShimmer(
                    modifier = Modifier.fillMaxWidth(0.4f).height(14.dp),
                )
                Spacer(Modifier.height(CSTheme.spacing.xl))
                CSShimmer(
                    modifier = Modifier.fillMaxWidth().height(CSTheme.sizes.buttonHeight),
                )
            }
            Spacer(Modifier.height(CSTheme.spacing.xl))
            repeat(3) {
                CSShimmerEpisodeCard()
            }
        }
    }
}

// --- Preview data ---

internal val previewTvShowState = TvShowDetailUiState(
    detail = DetailUiState(
        title = "Breaking Bad",
        year = "2008",
        rating = "9.5",
        duration = "49 min",
        contentRating = "TV-MA",
        genres = listOf("Drama", "Crime", "Thriller"),
        synopsis = "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.",
        cast = listOf(
            CastMember(id = "1", name = "Bryan Cranston", role = "Walter White"),
            CastMember(id = "2", name = "Aaron Paul", role = "Jesse Pinkman"),
            CastMember(id = "3", name = "Anna Gunn", role = "Skyler White"),
            CastMember(id = "4", name = "Dean Norris", role = "Hank"),
            CastMember(id = "5", name = "Betsy Brandt", role = "Marie"),
        ),
        recommendations = listOf(
            ContentItem(id = "rec_1", title = "Better Call Saul", subtitle = "2015"),
            ContentItem(id = "rec_2", title = "Ozark", subtitle = "2017"),
            ContentItem(id = "rec_3", title = "Narcos", subtitle = "2015"),
            ContentItem(id = "rec_4", title = "The Wire", subtitle = "2002"),
            ContentItem(id = "rec_5", title = "Fargo", subtitle = "2014"),
            ContentItem(id = "rec_6", title = "Peaky Blinders", subtitle = "2013"),
            ContentItem(id = "rec_7", title = "The Sopranos", subtitle = "1999"),
            ContentItem(id = "rec_8", title = "True Detective", subtitle = "2014"),
        ),
        trailers = listOf(
            TrailerItem(id = "t1", name = "Official Trailer"),
            TrailerItem(id = "t2", name = "Season 5 Trailer"),
        ),
    ),
    seasons = listOf(
        SeasonInfo(name = "Season 1", seasonNumber = 1, episodeCount = 7),
        SeasonInfo(name = "Season 2", seasonNumber = 2, episodeCount = 13),
        SeasonInfo(name = "Season 3", seasonNumber = 3, episodeCount = 13),
        SeasonInfo(name = "Season 4", seasonNumber = 4, episodeCount = 13),
        SeasonInfo(name = "Season 5", seasonNumber = 5, episodeCount = 16),
    ),
    episodes = listOf(
        EpisodeItem(
            id = "e1",
            title = "Pilot",
            episodeNumber = "S1 · E1",
            duration = "58 min",
            description = "Diagnosed with terminal lung cancer, chemistry teacher Walter White teams up with former student Jesse Pinkman to cook and sell crystal meth.",
            progressPercent = 1.0f,
        ),
        EpisodeItem(
            id = "e2",
            title = "Cat's in the Bag...",
            episodeNumber = "S1 · E2",
            duration = "48 min",
            description = "Walt and Jesse attempt to tie up loose ends. The desperate situation calls for a desperate measure.",
            progressPercent = 0.65f,
        ),
        EpisodeItem(
            id = "e3",
            title = "...And the Bag's in the River",
            episodeNumber = "S1 · E3",
            duration = "48 min",
            description = "Walt is faced with an agonizing decision that has unexpected consequences.",
        ),
        EpisodeItem(
            id = "e4",
            title = "Cancer Man",
            episodeNumber = "S1 · E4",
            duration = "48 min",
            description = "Walt tells the family he has cancer. Jesse tries to make a solo drug deal.",
        ),
    ),
)

@Preview
@Composable
private fun TvShowDetailScreenPreview() {
    CSTheme {
        TvShowDetailScreen(state = previewTvShowState)
    }
}

@Preview
@Composable
private fun TvShowDetailLoadingPreview() {
    CSTheme {
        TvShowDetailScreen(
            state = TvShowDetailUiState(detail = DetailUiState(isLoading = true)),
        )
    }
}
