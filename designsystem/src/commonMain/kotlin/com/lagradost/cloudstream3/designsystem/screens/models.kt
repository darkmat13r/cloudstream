package com.lagradost.cloudstream3.designsystem.screens

/**
 * UI state models for screen composables.
 * These are presentation-layer models decoupled from API/domain models.
 * Map your domain models (LoadResponse, SearchResponse, etc.) to these before passing to screens.
 */

// ===== Shared =====

data class ContentItem(
    val id: String,
    val title: String,
    val imageUrl: String? = null,
    val subtitle: String? = null,
    val progressPercent: Float? = null,
    val badgeText: String? = null,
)

data class CastMember(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val role: String? = null,
)

data class TrailerItem(
    val id: String,
    val name: String,
    val thumbnailUrl: String? = null,
)

// ===== Home =====

data class HomeUiState(
    val heroItems: List<HeroItem> = emptyList(),
    val sections: List<ContentSection> = emptyList(),
    val isLoading: Boolean = false,
)

data class HeroItem(
    val id: String,
    val title: String,
    val imageUrl: String? = null,
    val logoUrl: String? = null,
    val subtitle: String? = null,
    val metadata: String? = null,
    val progressPercent: Float? = null,
)

data class ContentSection(
    val title: String,
    val items: List<ContentItem>,
    val type: ContentSectionType = ContentSectionType.Poster,
)

enum class ContentSectionType {
    Poster,
    Landscape,
}

// ===== Detail (Movie / TV Show) =====

data class DetailUiState(
    val title: String = "",
    val backdropUrl: String? = null,
    val posterUrl: String? = null,
    val logoUrl: String? = null,
    val year: String? = null,
    val rating: String? = null,
    val duration: String? = null,
    val genres: List<String> = emptyList(),
    val synopsis: String? = null,
    val contentRating: String? = null,
    val cast: List<CastMember> = emptyList(),
    val recommendations: List<ContentItem> = emptyList(),
    val trailers: List<TrailerItem> = emptyList(),
    val isInWatchlist: Boolean = false,
    val isLoading: Boolean = false,
)

data class TvShowDetailUiState(
    val detail: DetailUiState = DetailUiState(),
    val seasons: List<SeasonInfo> = emptyList(),
    val selectedSeasonIndex: Int = 0,
    val episodes: List<EpisodeItem> = emptyList(),
)

data class SeasonInfo(
    val name: String,
    val seasonNumber: Int,
    val episodeCount: Int = 0,
)

data class EpisodeItem(
    val id: String,
    val title: String,
    val episodeNumber: String? = null,
    val thumbnailUrl: String? = null,
    val duration: String? = null,
    val description: String? = null,
    val progressPercent: Float? = null,
)

data class MovieDetailUiState(
    val detail: DetailUiState = DetailUiState(),
)
