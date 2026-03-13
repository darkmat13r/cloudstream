package com.lagradost.cloudstream3.designsystem.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.CSHeroCard
import com.lagradost.cloudstream3.designsystem.components.CSPillButton
import com.lagradost.cloudstream3.designsystem.components.CSPrimaryButton
import com.lagradost.cloudstream3.designsystem.screens.HeroItem
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hero carousel — full-width pager for featured/trending content.
 *
 * Inspired by Netflix/HBO Max hero sections:
 * - 35-40% viewport height
 * - Full-bleed images with gradient scrim
 * - Page indicator dots
 * - Title + subtitle + CTA overlay
 */
@Composable
fun HeroCarousel(
    items: List<HeroItem>,
    modifier: Modifier = Modifier,
    onItemClick: (HeroItem) -> Unit = {},
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { items.size })

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val item = items[page]
            CSHeroCard(
                imageUrl = item.imageUrl,
                title = item.title,
                subtitle = item.subtitle,
                metadata = item.metadata,
                onClick = { onItemClick(item) },
                actionContent = {
                    CSPrimaryButton(
                        text = "Play",
                        onClick = { onItemClick(item) },
                        icon = CSIcons.Play,
                        modifier = Modifier.weight(1f),
                    )
                    CSPillButton(
                        text = "My List",
                        onClick = {},
                    )
                },
            )
        }

        // Page indicator dots
        if (items.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = CSTheme.spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.xs),
            ) {
                repeat(items.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isActive) CSTheme.colors.textPrimary
                                else CSTheme.colors.textQuaternary,
                            ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HeroCarouselPreview() {
    CSTheme {
        HeroCarousel(
            items = listOf(
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
                    metadata = "Sci-Fi · Horror",
                ),
            ),
        )
    }
}
