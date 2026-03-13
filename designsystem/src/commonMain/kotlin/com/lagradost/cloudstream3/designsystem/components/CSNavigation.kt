package com.lagradost.cloudstream3.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.foundation.csFocusable
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Bottom navigation tab item data.
 */
data class CSTabItem(
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val label: String,
    val badge: Int? = null,
)

/**
 * Bottom tab bar — 5-tab navigation bar used on mobile.
 * Dark background matching page, white active / gray inactive states.
 */
@Composable
fun CSBottomBar(
    items: List<CSTabItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.bottomBarHeight)
            .background(CSTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            val interactionSource = remember { MutableInteractionSource() }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onItemSelected(index) },
                    )
                    .padding(vertical = CSTheme.spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = if (selected) item.selectedIcon else item.icon,
                    contentDescription = item.label,
                    tint = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.textQuaternary,
                    modifier = Modifier.size(CSTheme.sizes.iconLg),
                )
                Spacer(Modifier.height(CSTheme.spacing.xxs))
                Text(
                    text = item.label,
                    style = CSTheme.typography.overline,
                    color = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.textQuaternary,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/**
 * Top app bar with title, optional navigation icon, and action icons.
 * Transparent/matching background as in Netflix/HBO Max.
 */
@Composable
fun CSTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(CSTheme.sizes.topBarHeight)
            .background(backgroundColor)
            .padding(horizontal = CSTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon != null) {
            navigationIcon()
            Spacer(Modifier.width(CSTheme.spacing.md))
        }

        Text(
            text = title,
            style = CSTheme.typography.headlineMedium,
            color = CSTheme.colors.textPrimary,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.sm),
                content = actions,
            )
        }
    }
}

/**
 * Side rail navigation for tablet/TV — vertical icon strip.
 * Replaces bottom bar on larger screens.
 */
@Composable
fun CSSideRail(
    items: List<CSTabItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
) {
    val width = if (expanded) CSTheme.sizes.sideRailExpandedWidth else CSTheme.sizes.sideRailWidth

    Column(
        modifier = modifier
            .width(width)
            .fillMaxHeight()
            .background(CSTheme.colors.backgroundElevated)
            .padding(vertical = CSTheme.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CSTheme.spacing.xxs),
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            val interactionSource = remember { MutableInteractionSource() }

            Row(
                modifier = Modifier
                    .then(if (expanded) Modifier.fillMaxWidth() else Modifier)
                    .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.md)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onItemSelected(index) },
                    )
                    .then(
                        if (selected) Modifier.background(
                            CSTheme.colors.surfaceTertiary,
                            CSTheme.shapes.md,
                        ) else Modifier
                    )
                    .padding(
                        horizontal = CSTheme.spacing.lg,
                        vertical = CSTheme.spacing.md,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = if (selected) item.selectedIcon else item.icon,
                    contentDescription = item.label,
                    tint = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.textTertiary,
                    modifier = Modifier.size(CSTheme.sizes.iconLg),
                )
                if (expanded) {
                    Spacer(Modifier.width(CSTheme.spacing.md))
                    Text(
                        text = item.label,
                        style = CSTheme.typography.titleMedium,
                        color = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.textTertiary,
                    )
                }
            }
        }
    }
}

/**
 * Horizontal category tabs — Netflix/YouTube style filter pills.
 * Scrollable row of pill-shaped tabs.
 */
@Composable
fun CSCategoryTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CSTheme.spacing.lg),
        horizontalArrangement = Arrangement.spacedBy(CSTheme.spacing.sm),
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = index == selectedIndex
            val interactionSource = remember { MutableInteractionSource() }

            Text(
                text = tab,
                style = CSTheme.typography.titleMedium,
                color = if (selected) CSTheme.colors.textOnAccent else CSTheme.colors.textPrimary,
                modifier = Modifier
                    .csFocusable(interactionSource = interactionSource, shape = CSTheme.shapes.pill)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onTabSelected(index) },
                    )
                    .background(
                        color = if (selected) CSTheme.colors.textPrimary else CSTheme.colors.surfaceTertiary,
                        shape = CSTheme.shapes.pill,
                    )
                    .padding(horizontal = CSTheme.spacing.lg, vertical = CSTheme.spacing.sm),
            )
        }
    }
}

// --- Previews ---

@Preview
@Composable
private fun CSCategoryTabsPreview() {
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
