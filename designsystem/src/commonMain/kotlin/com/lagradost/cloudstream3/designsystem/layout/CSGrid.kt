package com.lagradost.cloudstream3.designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.CSPosterCard
import com.lagradost.cloudstream3.designsystem.components.CSSectionHeader
import com.lagradost.cloudstream3.designsystem.foundation.contentPadding as windowContentPadding
import com.lagradost.cloudstream3.designsystem.foundation.landscapeColumns
import com.lagradost.cloudstream3.designsystem.foundation.posterColumns
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Content type hint for automatic column count calculation.
 */
enum class CSGridContentType {
    /** 2:3 portrait posters — more columns */
    Poster,
    /** 16:9 landscape thumbnails — fewer columns */
    Landscape,
}

/**
 * Responsive grid — adapts column count based on window size and content type.
 *
 * Wraps [LazyVerticalGrid] with sensible defaults for streaming content:
 * - Poster grids: 3 cols (phone) → 4 (tablet) → 5 (desktop) → 7 (TV)
 * - Landscape grids: 2 cols (phone) → 3 (tablet/desktop) → 4 (TV)
 *
 * @param columns Explicit column count. If null, auto-calculated from [contentType].
 * @param contentType Hint for auto column count. Ignored when [columns] is set.
 * @param itemSpacing Gap between grid items.
 * @param padding Padding around the grid edges. If null, uses responsive defaults.
 */
@Composable
fun CSGrid(
    modifier: Modifier = Modifier,
    columns: Int? = null,
    contentType: CSGridContentType = CSGridContentType.Poster,
    itemSpacing: Dp = CSTheme.spacing.sm,
    padding: PaddingValues? = null,
    content: LazyGridScope.() -> Unit,
) {
    val isTV = CSTheme.isTV
    val windowSize = CSTheme.windowSize

    val resolvedColumns = columns ?: when (contentType) {
        CSGridContentType.Poster -> windowSize.posterColumns(isTV)
        CSGridContentType.Landscape -> windowSize.landscapeColumns(isTV)
    }

    val resolvedPadding = padding ?: PaddingValues(
        horizontal = windowSize.windowContentPadding(),
        vertical = CSTheme.spacing.sm,
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(resolvedColumns),
        modifier = modifier.fillMaxSize(),
        contentPadding = resolvedPadding,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        verticalArrangement = Arrangement.spacedBy(itemSpacing),
        content = content,
    )
}

/**
 * Helper — full-width header spanning all columns inside a [CSGrid].
 */
fun LazyGridScope.gridHeader(
    content: @Composable () -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        content()
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSGridPosterPreview() {
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

@Preview
@Composable
private fun CSGridLandscapePreview() {
    CSTheme {
        CSGrid(
            modifier = Modifier.background(CSTheme.colors.background),
            contentType = CSGridContentType.Landscape,
        ) {
            gridHeader {
                CSSectionHeader(title = "Continue Watching")
            }
            items(6) { index ->
                CSPosterCard(
                    imageUrl = null,
                    title = "Episode ${index + 1}",
                    onClick = {},
                    width = Dp.Unspecified,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
