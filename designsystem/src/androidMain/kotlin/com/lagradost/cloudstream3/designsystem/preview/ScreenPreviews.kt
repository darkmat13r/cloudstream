package com.lagradost.cloudstream3.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lagradost.cloudstream3.designsystem.foundation.calculateWindowSizeClass
import com.lagradost.cloudstream3.designsystem.screens.*
import com.lagradost.cloudstream3.designsystem.screens.detail.*
import com.lagradost.cloudstream3.designsystem.screens.home.*
import com.lagradost.cloudstream3.designsystem.theme.CSTheme

// ===== Home Screen — Phone =====

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Home · Phone")
@Composable
private fun HomePhonePreview() {
    CSTheme {
        HomeScreen(state = previewHomeState)
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Home · Phone Loading")
@Composable
private fun HomePhoneLoadingPreview() {
    CSTheme {
        HomeScreen(state = HomeUiState(isLoading = true))
    }
}

// ===== Home Screen — Tablet =====

@Preview(showBackground = true, widthDp = 800, heightDp = 1200, name = "Home · Tablet")
@Composable
private fun HomeTabletPreview() {
    CSTheme(windowSizeClass = calculateWindowSizeClass(800.dp, 1200.dp)) {
        HomeScreen(state = previewHomeState)
    }
}

// ===== Home Screen — TV =====

@Preview(showBackground = true, widthDp = 1280, heightDp = 720, name = "Home · TV")
@Composable
private fun HomeTvPreview() {
    CSTheme(
        isTV = true,
        windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
    ) {
        HomeScreen(state = previewHomeState)
    }
}

// ===== TV Show Detail — Phone =====

@Preview(showBackground = true, widthDp = 400, heightDp = 900, name = "TV Show · Phone")
@Composable
private fun TvShowDetailPhonePreview() {
    CSTheme {
        TvShowDetailScreen(state = previewTvShowState)
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900, name = "TV Show · Phone Loading")
@Composable
private fun TvShowDetailLoadingPreview() {
    CSTheme {
        TvShowDetailScreen(
            state = TvShowDetailUiState(detail = DetailUiState(isLoading = true)),
        )
    }
}

// ===== TV Show Detail — Tablet =====

@Preview(showBackground = true, widthDp = 800, heightDp = 1200, name = "TV Show · Tablet")
@Composable
private fun TvShowDetailTabletPreview() {
    CSTheme(windowSizeClass = calculateWindowSizeClass(800.dp, 1200.dp)) {
        TvShowDetailScreen(state = previewTvShowState)
    }
}

// ===== TV Show Detail — TV =====

@Preview(showBackground = true, widthDp = 1280, heightDp = 720, name = "TV Show · TV")
@Composable
private fun TvShowDetailTvPreview() {
    CSTheme(
        isTV = true,
        windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
    ) {
        TvShowDetailScreen(state = previewTvShowState)
    }
}

// ===== Movie Detail — Phone =====

@Preview(showBackground = true, widthDp = 400, heightDp = 900, name = "Movie · Phone")
@Composable
private fun MovieDetailPhonePreview() {
    CSTheme {
        MovieDetailScreen(state = previewMovieState)
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900, name = "Movie · Phone Loading")
@Composable
private fun MovieDetailLoadingPreview() {
    CSTheme {
        MovieDetailScreen(
            state = MovieDetailUiState(detail = DetailUiState(isLoading = true)),
        )
    }
}

// ===== Movie Detail — Tablet =====

@Preview(showBackground = true, widthDp = 800, heightDp = 1200, name = "Movie · Tablet")
@Composable
private fun MovieDetailTabletPreview() {
    CSTheme(windowSizeClass = calculateWindowSizeClass(800.dp, 1200.dp)) {
        MovieDetailScreen(state = previewMovieState)
    }
}

// ===== Movie Detail — TV =====

@Preview(showBackground = true, widthDp = 1280, heightDp = 720, name = "Movie · TV")
@Composable
private fun MovieDetailTvPreview() {
    CSTheme(
        isTV = true,
        windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
    ) {
        MovieDetailScreen(state = previewMovieState)
    }
}
