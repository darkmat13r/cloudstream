package com.lagradost.cloudstream3.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.*
import com.lagradost.cloudstream3.designsystem.layout.*
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons
import com.lagradost.cloudstream3.designsystem.theme.CSTheme

// ===== CSButton Previews =====

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    CSTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSPrimaryButton(text = "Play", onClick = {}, icon = CSIcons.Play)
            CSPrimaryButton(text = "Play", onClick = {}, enabled = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview() {
    CSTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSSecondaryButton(text = "Add to List", onClick = {})
            CSAccentButton(text = "Resume", onClick = {})
            CSPillButton(text = "Trailers", onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IconActionButtonPreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CSIconActionButton(icon = CSIcons.Add, label = "My List", onClick = {})
            CSIconActionButton(icon = CSIcons.Download, label = "Download", onClick = {})
            CSIconActionButton(icon = CSIcons.Share, label = "Share", onClick = {})
            CSIconActionButton(icon = CSIcons.ThumbUp, label = "Rate", onClick = {})
        }
    }
}

// ===== CSCard Previews =====

@Preview(showBackground = true)
@Composable
private fun PosterCardPreview() {
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

@Preview(showBackground = true)
@Composable
private fun LandscapeCardPreview() {
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

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun HeroCardPreview() {
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

// ===== CSNavigation Previews =====

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun CategoryTabsPreview() {
    CSTheme {
        Column(
            modifier = Modifier.background(CSTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CSTopBar(title = "For You")
            CSCategoryTabs(
                tabs = listOf("All", "Shows", "Movies", "Categories"),
                selectedIndex = 0,
                onTabSelected = {},
            )
        }
    }
}

// ===== CSContentRow Previews =====

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun SectionHeaderPreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CSSectionHeader(title = "Continue Watching")
            CSSectionHeader(title = "Popular on CloudStream", onSeeAllClick = {})
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun ContentRowPreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .fillMaxWidth(),
        ) {
            CSContentRow(title = "Trending Now", onSeeAllClick = {}) {
                repeat(5) {
                    CSPosterCard(
                        imageUrl = null,
                        title = "Show ${it + 1}",
                        onClick = {},
                        width = 100.dp,
                    )
                }
            }
        }
    }
}

// ===== CSInput Previews =====

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun SearchBarPreview() {
    CSTheme {
        Column(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CSSearchBar(query = "", onQueryChange = {})
            CSSearchBar(query = "Breaking Bad", onQueryChange = {}, onClear = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipPreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSChip(label = "All", selected = true, onClick = {})
            CSChip(label = "Movies", selected = false, onClick = {})
            CSChip(label = "Shows", selected = false, onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryTilePreview() {
    CSTheme {
        Row(
            modifier = Modifier
                .background(CSTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CSCategoryTile(
                title = "Action",
                backgroundColor = Color(0xFFE53935),
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            CSCategoryTile(
                title = "Comedy",
                backgroundColor = Color(0xFF1E88E5),
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}

// ===== CSMedia Previews =====

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun EpisodeCardPreview() {
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

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun PlaybackProgressBarPreview() {
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

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun SynopsisPreview() {
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

// ===== CSFeedback Previews =====

@Preview(showBackground = true)
@Composable
private fun BadgePreview() {
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
            CSBadge(
                text = "4K",
                containerColor = CSTheme.colors.surfaceTertiary,
                contentColor = CSTheme.colors.textPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarPreview() {
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

@Preview(showBackground = true)
@Composable
private fun ShimmerPreview() {
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

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun ShimmerEpisodePreview() {
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

// ===== CSGrid Previews =====

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
private fun GridPosterPreview() {
    CSTheme {
        CSGrid(
            modifier = Modifier.background(CSTheme.colors.background),
            contentType = CSGridContentType.Poster,
        ) {
            gridHeader {
                CSSectionHeader(title = "Popular Movies")
            }
            items(12) { index ->
                CSPosterCard(
                    imageUrl = null,
                    title = "Movie ${index + 1}",
                    onClick = {},
                    width = Dp.Unspecified,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

// ===== CSScaffold Previews =====

@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
private fun ScaffoldCompactPreview() {
    CSTheme {
        CSScaffold(
            tabs = listOf(
                CSTabItem(icon = CSIcons.Home, label = "Home"),
                CSTabItem(icon = CSIcons.Search, label = "Search"),
                CSTabItem(icon = CSIcons.Play, label = "Coming Soon"),
                CSTabItem(icon = CSIcons.Profile, label = "Downloads"),
                CSTabItem(icon = CSIcons.Settings, label = "More"),
            ),
            selectedTabIndex = 0,
            onTabSelected = {},
            topBar = { CSTopBar(title = "CloudStream") },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CSTheme.colors.background)
                    .padding(it),
            )
        }
    }
}
