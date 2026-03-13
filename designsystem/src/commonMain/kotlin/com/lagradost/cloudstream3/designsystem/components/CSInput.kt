package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import com.lagradost.cloudstream3.designsystem.tokens.AlphaDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Search bar — full-width rounded search input.
 * Dark theme: dark gray background, white text, gray placeholder.
 */
@Composable
fun CSSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search shows, movies...",
    onClear: (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.searchBarHeight)
            .background(
                color = CSTheme.colors.surfaceSecondary,
                shape = CSTheme.shapes.md,
            )
            .then(
                if (isFocused) Modifier.border(
                    width = 1.dp,
                    color = CSTheme.colors.accentPrimary,
                    shape = CSTheme.shapes.md,
                ) else Modifier
            )
            .padding(horizontal = CSTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = CSIcons.Search,
            contentDescription = "Search",
            tint = CSTheme.colors.textTertiary,
            modifier = Modifier.size(CSTheme.sizes.iconMd),
        )
        Spacer(Modifier.width(CSTheme.spacing.sm))
        Box(modifier = Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    style = CSTheme.typography.bodyMedium,
                    color = CSTheme.colors.textTertiary,
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = CSTheme.typography.bodyMedium.copy(
                    color = CSTheme.colors.textPrimary,
                ),
                cursorBrush = SolidColor(CSTheme.colors.accentPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
            )
        }
        if (query.isNotEmpty() && onClear != null) {
            Spacer(Modifier.width(CSTheme.spacing.sm))
            Icon(
                imageVector = CSIcons.Close,
                contentDescription = "Clear",
                tint = CSTheme.colors.textTertiary,
                modifier = Modifier
                    .size(CSTheme.sizes.iconMd)
                    .clickable(onClick = onClear),
            )
        }
    }
}

/**
 * Filter chip — Netflix/YouTube style selectable pill.
 * Used for genre/category filtering.
 */
@Composable
fun CSChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor = when {
        selected -> CSTheme.colors.textPrimary
        else -> CSTheme.colors.surfaceTertiary
    }
    val contentColor = when {
        selected -> CSTheme.colors.textOnAccent
        else -> CSTheme.colors.textPrimary
    }
    val alpha = if (enabled) AlphaDefaults.Full else AlphaDefaults.Disabled

    Text(
        text = label,
        style = CSTheme.typography.titleMedium,
        color = contentColor.copy(alpha = alpha),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .height(CSTheme.sizes.chipHeight)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.pill)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .background(
                color = backgroundColor.copy(alpha = alpha),
                shape = CSTheme.shapes.pill,
            )
            .padding(horizontal = CSTheme.spacing.lg, vertical = CSTheme.spacing.sm)
            .wrapContentHeight(Alignment.CenterVertically),
    )
}

/**
 * Colorful category/genre tile — Spotify-style genre browsing.
 * 2-column grid tile with background color and title.
 */
@Composable
fun CSCategoryTile(
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.categoryTileHeight)
            .background(backgroundColor, CSTheme.shapes.md)
            .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(CSTheme.spacing.md),
        contentAlignment = Alignment.TopStart,
    ) {
        Text(
            text = title,
            style = CSTheme.typography.titleLarge,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSSearchBarPreview() {
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

@Preview
@Composable
private fun CSChipPreview() {
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

@Preview
@Composable
private fun CSCategoryTilePreview() {
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
