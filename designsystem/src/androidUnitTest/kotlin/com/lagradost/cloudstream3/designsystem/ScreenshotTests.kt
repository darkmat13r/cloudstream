package com.lagradost.cloudstream3.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.lagradost.cloudstream3.designsystem.foundation.calculateWindowSizeClass
import com.lagradost.cloudstream3.designsystem.screens.*
import com.lagradost.cloudstream3.designsystem.screens.detail.*
import com.lagradost.cloudstream3.designsystem.screens.home.*
import com.lagradost.cloudstream3.designsystem.theme.CSTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class ScreenshotTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ===== HOME SCREEN =====

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun homeScreen_phone() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(393.dp, 851.dp),
            ) {
                HomeScreen(state = previewHomeState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/home_phone.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.MediumTablet)
    fun homeScreen_tablet() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(800.dp, 1280.dp),
            ) {
                HomeScreen(state = previewHomeState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/home_tablet.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.SmallDesktop)
    fun homeScreen_tv() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                isTV = true,
                windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
            ) {
                HomeScreen(state = previewHomeState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/home_tv.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun homeScreen_phone_loading() {
        composeTestRule.setContent {
            CSTheme(darkTheme = true) {
                HomeScreen(state = HomeUiState(isLoading = true))
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/home_phone_loading.png")
    }

    // ===== TV SHOW DETAIL =====

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun tvShowDetail_phone() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(393.dp, 851.dp),
            ) {
                TvShowDetailScreen(state = previewTvShowState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/tvshow_detail_phone.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.MediumTablet)
    fun tvShowDetail_tablet() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(800.dp, 1280.dp),
            ) {
                TvShowDetailScreen(state = previewTvShowState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/tvshow_detail_tablet.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.SmallDesktop)
    fun tvShowDetail_tv() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                isTV = true,
                windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
            ) {
                TvShowDetailScreen(state = previewTvShowState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/tvshow_detail_tv.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun tvShowDetail_phone_loading() {
        composeTestRule.setContent {
            CSTheme(darkTheme = true) {
                TvShowDetailScreen(
                    state = TvShowDetailUiState(detail = DetailUiState(isLoading = true)),
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/tvshow_detail_phone_loading.png")
    }

    // ===== MOVIE DETAIL =====

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun movieDetail_phone() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(393.dp, 851.dp),
            ) {
                MovieDetailScreen(state = previewMovieState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_detail_phone.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.MediumTablet)
    fun movieDetail_tablet() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                windowSizeClass = calculateWindowSizeClass(800.dp, 1280.dp),
            ) {
                MovieDetailScreen(state = previewMovieState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_detail_tablet.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.SmallDesktop)
    fun movieDetail_tv() {
        composeTestRule.setContent {
            CSTheme(
                darkTheme = true,
                isTV = true,
                windowSizeClass = calculateWindowSizeClass(1280.dp, 720.dp),
            ) {
                MovieDetailScreen(state = previewMovieState)
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_detail_tv.png")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun movieDetail_phone_loading() {
        composeTestRule.setContent {
            CSTheme(darkTheme = true) {
                MovieDetailScreen(
                    state = MovieDetailUiState(detail = DetailUiState(isLoading = true)),
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_detail_phone_loading.png")
    }
}
