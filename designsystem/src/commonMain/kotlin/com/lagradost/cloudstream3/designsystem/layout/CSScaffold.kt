package com.lagradost.cloudstream3.designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.lagradost.cloudstream3.designsystem.tokens.CSIcons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.components.CSBottomBar
import com.lagradost.cloudstream3.designsystem.components.CSSideRail
import com.lagradost.cloudstream3.designsystem.components.CSTabItem
import com.lagradost.cloudstream3.designsystem.components.CSTopBar
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Responsive scaffold — switches between bottom bar (phone) and side rail (tablet/TV).
 *
 * On [WindowWidthClass.Compact]: bottom bar with optional top bar.
 * On [WindowWidthClass.Medium]: collapsed side rail (icons only).
 * On [WindowWidthClass.Expanded] / TV: expanded side rail (icons + labels).
 */
@Composable
fun CSScaffold(
    tabs: List<CSTabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val windowSize = CSTheme.windowSize
    val isTV = CSTheme.isTV

    when {
        // Expanded or TV → side rail (expanded labels on TV/large screens)
        windowSize.isExpanded || isTV -> {
            Row(modifier = modifier.fillMaxSize()) {
                CSSideRail(
                    items = tabs,
                    selectedIndex = selectedTabIndex,
                    onItemSelected = onTabSelected,
                    expanded = true,
                )
                Column(modifier = Modifier.weight(1f)) {
                    topBar?.invoke()
                    content(PaddingValues())
                }
            }
        }
        // Medium → collapsed side rail
        windowSize.isMedium -> {
            Row(modifier = modifier.fillMaxSize()) {
                CSSideRail(
                    items = tabs,
                    selectedIndex = selectedTabIndex,
                    onItemSelected = onTabSelected,
                    expanded = false,
                )
                Column(modifier = Modifier.weight(1f)) {
                    topBar?.invoke()
                    content(PaddingValues())
                }
            }
        }
        // Compact → bottom bar
        else -> {
            Column(modifier = modifier.fillMaxSize()) {
                topBar?.invoke()
                Box(modifier = Modifier.weight(1f)) {
                    content(
                        PaddingValues(bottom = CSTheme.sizes.bottomBarHeight)
                    )
                }
                CSBottomBar(
                    items = tabs,
                    selectedIndex = selectedTabIndex,
                    onItemSelected = onTabSelected,
                )
            }
        }
    }
}

// --- Previews ---

private val previewTabs = listOf(
    CSTabItem(icon = CSIcons.Home, label = "Home"),
    CSTabItem(icon = CSIcons.Search, label = "Search"),
    CSTabItem(icon = CSIcons.Play, label = "Coming Soon"),
    CSTabItem(icon = CSIcons.Profile, label = "Downloads"),
    CSTabItem(icon = CSIcons.Settings, label = "More"),
)

@Preview
@Composable
private fun CSScaffoldCompactPreview() {
    CSTheme {
        CSScaffold(
            tabs = previewTabs,
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
