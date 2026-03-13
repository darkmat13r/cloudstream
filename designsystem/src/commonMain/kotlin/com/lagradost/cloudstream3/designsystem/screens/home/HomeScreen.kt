package com.lagradost.cloudstream3.designsystem.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.*
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.screens.*
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Home screen — Netflix-style scrollable feed.
 *
 * Layout:
 * - Hero carousel (35-40% viewport)
 * - Horizontal content rows (poster or landscape cards)
 * - Each row has a section header with optional "See All"
 */
@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onHeroClick: (HeroItem) -> Unit = {},
    onItemClick: (ContentItem) -> Unit = {},
    onSeeAllClick: (ContentSection) -> Unit = {},
) {
    if (state.isLoading) {
        HomeShimmer(modifier = modifier)
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CSTheme.colors.background),
    ) {
        // Hero carousel
        if (state.heroItems.isNotEmpty()) {
            item(key = "hero") {
                HeroCarousel(
                    items = state.heroItems,
                    onItemClick = onHeroClick,
                )
            }
        }

        // Content sections
        items(
            items = state.sections,
            key = { it.title },
        ) { section ->
            Spacer(Modifier.height(CSTheme.spacing.md))
            when (section.type) {
                ContentSectionType.Poster -> {
                    CSContentRow(
                        title = section.title,
                        onSeeAllClick = { onSeeAllClick(section) },
                    ) {
                        section.items.forEach { item ->
                            CSPosterCard(
                                imageUrl = item.imageUrl,
                                title = item.title,
                                subtitle = item.subtitle,
                                onClick = { onItemClick(item) },
                                progressPercent = item.progressPercent,
                                badge = item.badgeText?.let { text ->
                                    { CSBadge(text = text) }
                                },
                            )
                        }
                    }
                }
                ContentSectionType.Landscape -> {
                    CSContentRow(
                        title = section.title,
                        onSeeAllClick = { onSeeAllClick(section) },
                    ) {
                        section.items.forEach { item ->
                            CSLandscapeCard(
                                imageUrl = item.imageUrl,
                                title = item.title,
                                subtitle = item.subtitle,
                                onClick = { onItemClick(item) },
                                progressPercent = item.progressPercent,
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(CSTheme.spacing.xxxl)) }
    }
}

/**
 * Shimmer loading placeholder for the home screen.
 */
@Composable
private fun HomeShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CSTheme.colors.background),
    ) {
        // Hero shimmer
        item {
            CSShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
            )
        }

        // Row shimmers
        items(4) {
            Spacer(Modifier.height(CSTheme.spacing.xl))
            CSSectionHeader(title = "")
            Spacer(Modifier.height(CSTheme.spacing.sm))
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

internal val previewHomeState = HomeUiState(
    heroItems = listOf(
        HeroItem(
            id = "1",
            title = "House of the Dragon",
            subtitle = "HBO Original",
            metadata = "Drama · Fantasy · 2024",
        ),
        HeroItem(
            id = "2",
            title = "Stranger Things",
            subtitle = "Netflix Original",
            metadata = "Sci-Fi · Horror · 2025",
        ),
        HeroItem(
            id = "3",
            title = "The Last of Us",
            subtitle = "HBO Original",
            metadata = "Drama · Action · 2024",
        ),
    ),
    sections = listOf(
        ContentSection(
            title = "Continue Watching",
            type = ContentSectionType.Landscape,
            items = listOf(
                ContentItem(id = "cw_1", title = "Breaking Bad", subtitle = "S3 · E7", progressPercent = 0.72f),
                ContentItem(id = "cw_2", title = "The Bear", subtitle = "S2 · E4", progressPercent = 0.35f),
                ContentItem(id = "cw_3", title = "Severance", subtitle = "S2 · E1", progressPercent = 0.12f),
                ContentItem(id = "cw_4", title = "Shogun", subtitle = "S1 · E9", progressPercent = 0.88f),
                ContentItem(id = "cw_5", title = "True Detective", subtitle = "S4 · E5", progressPercent = 0.50f),
            ),
        ),
        ContentSection(
            title = "Trending Now",
            items = listOf(
                ContentItem(id = "tr_1", title = "Oppenheimer", badgeText = "TOP 1"),
                ContentItem(id = "tr_2", title = "Dune: Part Two", badgeText = "TOP 2"),
                ContentItem(id = "tr_3", title = "Poor Things", badgeText = "TOP 3"),
                ContentItem(id = "tr_4", title = "Killers of the Flower Moon"),
                ContentItem(id = "tr_5", title = "The Holdovers"),
                ContentItem(id = "tr_6", title = "Past Lives"),
                ContentItem(id = "tr_7", title = "Anatomy of a Fall"),
                ContentItem(id = "tr_8", title = "The Zone of Interest"),
            ),
        ),
        ContentSection(
            title = "Popular on CloudStream",
            items = listOf(
                ContentItem(id = "pop_1", title = "Arcane", subtitle = "2024", badgeText = "NEW"),
                ContentItem(id = "pop_2", title = "Squid Game", subtitle = "2024"),
                ContentItem(id = "pop_3", title = "Wednesday", subtitle = "2023"),
                ContentItem(id = "pop_4", title = "The Witcher", subtitle = "2023"),
                ContentItem(id = "pop_5", title = "One Piece", subtitle = "2024"),
                ContentItem(id = "pop_6", title = "Reacher", subtitle = "2024"),
                ContentItem(id = "pop_7", title = "Fallout", subtitle = "2024"),
                ContentItem(id = "pop_8", title = "3 Body Problem", subtitle = "2024"),
            ),
        ),
        ContentSection(
            title = "Action & Adventure",
            items = listOf(
                ContentItem(id = "act_1", title = "John Wick: Chapter 4"),
                ContentItem(id = "act_2", title = "Mission: Impossible"),
                ContentItem(id = "act_3", title = "The Equalizer 3"),
                ContentItem(id = "act_4", title = "Extraction 2"),
                ContentItem(id = "act_5", title = "Fast X"),
                ContentItem(id = "act_6", title = "Rebel Moon"),
                ContentItem(id = "act_7", title = "Napoleon"),
            ),
        ),
    ),
)

@Preview
@Composable
private fun HomeScreenPreview() {
    CSTheme {
        HomeScreen(state = previewHomeState)
    }
}

@Preview
@Composable
private fun HomeShimmerPreview() {
    CSTheme {
        HomeScreen(state = HomeUiState(isLoading = true))
    }
}
