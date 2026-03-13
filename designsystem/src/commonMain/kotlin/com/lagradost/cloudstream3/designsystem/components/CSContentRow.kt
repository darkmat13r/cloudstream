package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Section header with title and optional "See All" action.
 * Netflix/HBO Max style: bold left-aligned title with chevron link.
 */
@Composable
fun CSSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    onSeeAllClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CSTheme.spacing.lg,
                vertical = CSTheme.spacing.sm,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = CSTheme.typography.headlineSmall,
            color = CSTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        if (onSeeAllClick != null) {
            Text(
                text = "See All >",
                style = CSTheme.typography.titleSmall,
                color = CSTheme.colors.textLink,
                modifier = Modifier.clickable(onClick = onSeeAllClick),
            )
        }
    }
}

/**
 * Horizontal scrollable content row with section header.
 * The primary content browsing pattern used by all streaming platforms.
 *
 * @param title Section title displayed above the row
 * @param onSeeAllClick Optional "See All" action
 * @param contentPadding Horizontal padding for the first/last items
 * @param itemSpacing Gap between items
 * @param content Row items — typically [CSPosterCard] or [CSLandscapeCard]
 */
@Composable
fun CSContentRow(
    title: String,
    modifier: Modifier = Modifier,
    onSeeAllClick: (() -> Unit)? = null,
    contentPadding: Dp = CSTheme.spacing.lg,
    itemSpacing: Dp = CSTheme.spacing.sm,
    content: @Composable RowScope.() -> Unit,
) {
    Column(modifier = modifier) {
        CSSectionHeader(
            title = title,
            onSeeAllClick = onSeeAllClick,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = contentPadding),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
            content = content,
        )
    }
}

/**
 * Content row using a list of data items with a composable item slot.
 * More practical for dynamic data.
 */
@Composable
fun <T> CSContentRow(
    title: String,
    items: List<T>,
    modifier: Modifier = Modifier,
    onSeeAllClick: (() -> Unit)? = null,
    contentPadding: Dp = CSTheme.spacing.lg,
    itemSpacing: Dp = CSTheme.spacing.sm,
    itemContent: @Composable (T) -> Unit,
) {
    Column(modifier = modifier) {
        CSSectionHeader(
            title = title,
            onSeeAllClick = onSeeAllClick,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = contentPadding),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        ) {
            items.forEach { item ->
                itemContent(item)
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSSectionHeaderPreview() {
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

@Preview
@Composable
private fun CSContentRowPreview() {
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
