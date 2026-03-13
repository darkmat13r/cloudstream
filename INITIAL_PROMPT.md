# CloudStream KMP App — Implementation Prompt

> **Purpose**: Copy this entire document as your initial prompt when starting a new Claude Code session. It provides full context to resume work on the KMP app implementation. Update the "Current Progress" section at the end of each session.

---

## Project Overview

CloudStream is a media center app being migrated from Android-only to **Kotlin Multiplatform (KMP)** targeting Android and iOS. The existing Android app lives in `app/` with XML views. We are building a new **Compose Multiplatform** UI layer using the design system in `designsystem/` and clean architecture in new KMP feature modules.

**Repository**: `/Volumes/ExtStorage/Projects/StreamingApp/cloudstream`

**CRITICAL RULE**: Every single feature from the existing Android app MUST be replicated. Before implementing any feature, ALWAYS read the existing Android implementation first (`app/src/main/java/com/lagradost/cloudstream3/`), understand what it does, then rebuild it with KMP clean architecture. No feature may be skipped or simplified.

---

## Architecture

### Clean Architecture + SOLID + Multimodule

```
:app                    — Android host (existing, legacy XML UI)
:library                — KMP shared core (APIs, extractors, network, plugins)
:designsystem           — KMP Compose UI components, tokens, theme
:core:common            — Shared utilities, extensions, dispatchers, Resource wrapper
:core:domain            — Use cases, repository interfaces, domain models
:core:data              — Repository implementations, data sources, mappers
:core:network           — Network client abstraction, interceptors, Cloudflare/DDoS bypass
:core:datastore         — Preferences, key-value storage (KMP), watch state persistence
:core:navigation        — Navigation graph, route definitions, deep link handling
:core:testing           — Shared test utilities, fakes, fixtures
:core:player            — Shared playback state, subtitle parsing, source management
:core:sync              — Sync provider interfaces, account management
:core:downloads         — Download state tracking, queue management
:feature:home           — Home screen feature
:feature:search         — Search & browse feature (includes quick search)
:feature:detail         — Content detail (movie/tvshow/anime) feature
:feature:player         — Video player feature (platform-specific renderers)
:feature:library        — Watchlist/library/bookmarks feature
:feature:downloads      — Download management feature
:feature:settings       — Settings & preferences feature (all sub-settings)
:feature:extensions     — Plugin/extension management feature
:feature:auth           — Authentication, accounts, sync providers, profiles
:feature:onboarding     — Setup wizard & onboarding feature
:feature:subtitles      — Subtitle settings & management
:feature:webview        — Embedded browser for auth/login flows
```

### Rules

1. **No static/hardcoded values** — All strings, colors, dimensions, icons come from design system tokens or string resources
2. **SOLID principles** — Single responsibility per class, depend on abstractions not concretions, open for extension
3. **Unidirectional data flow** — State flows down, events flow up. ViewModels expose `StateFlow<UiState>`, screens emit events via lambdas
4. **Repository pattern** — Domain layer defines interfaces, data layer implements them
5. **Use cases** — Each business operation is a single-purpose use case class with `operator fun invoke()`
6. **Mapper pattern** — Separate mappers between API models → domain models → UI models
7. **Constructor injection** — All dependencies injected via constructor, no service locators
8. **Testable** — Every ViewModel, UseCase, Repository, and Mapper must be unit-testable with fakes/mocks
9. **Feature parity** — Before building ANY feature, read the existing Android implementation. Replicate ALL capabilities
10. **Code index** — Before creating any new class, component, or constant, ALWAYS search the existing codebase first using Grep/Glob to check if it already exists

---

## Existing Code Index

### Design System (`designsystem/src/commonMain/`)

**ALWAYS reuse these — never recreate:**

#### Components
| Component | File | Purpose |
|-----------|------|---------|
| `CSPrimaryButton` | `components/CSButton.kt` | White CTA button (Play, Watch) |
| `CSSecondaryButton` | `components/CSButton.kt` | Outlined secondary button |
| `CSAccentButton` | `components/CSButton.kt` | Accent-colored CTA |
| `CSIconActionButton` | `components/CSButton.kt` | Vertical icon+label (My List, Share) |
| `CSPillButton` | `components/CSButton.kt` | Small pill button |
| `CSPosterCard` | `components/CSCard.kt` | 2:3 poster thumbnail |
| `CSLandscapeCard` | `components/CSCard.kt` | 16:9 landscape thumbnail |
| `CSHeroCard` | `components/CSCard.kt` | Full-width hero with gradient |
| `CSWatchProgress` | `components/CSCard.kt` | Progress bar overlay |
| `CSEpisodeCard` | `components/CSMedia.kt` | Episode row with thumbnail |
| `CSPlaybackProgressBar` | `components/CSMedia.kt` | Player progress bar |
| `CSMetadataRow` | `components/CSMedia.kt` | Year/rating/duration inline |
| `CSSynopsis` | `components/CSMedia.kt` | Expandable text |
| `CSSearchBar` | `components/CSInput.kt` | Search input |
| `CSChip` | `components/CSInput.kt` | Filter pill |
| `CSCategoryTile` | `components/CSInput.kt` | Browse category tile |
| `CSBadge` | `components/CSFeedback.kt` | Status badge (NEW, TOP) |
| `CSRatingBadge` | `components/CSFeedback.kt` | Maturity rating badge |
| `CSTop10Badge` | `components/CSFeedback.kt` | Top 10 rank badge |
| `CSAvatar` | `components/CSFeedback.kt` | Profile avatar circle |
| `CSShimmer` | `components/CSFeedback.kt` | Loading skeleton |
| `CSShimmerPosterCard` | `components/CSFeedback.kt` | Poster loading placeholder |
| `CSShimmerLandscapeCard` | `components/CSFeedback.kt` | Landscape loading placeholder |
| `CSShimmerEpisodeCard` | `components/CSFeedback.kt` | Episode loading placeholder |
| `CSBottomBar` | `components/CSNavigation.kt` | Mobile bottom tab bar |
| `CSTopBar` | `components/CSNavigation.kt` | App bar with actions |
| `CSSideRail` | `components/CSNavigation.kt` | Tablet/TV side navigation |
| `CSCategoryTabs` | `components/CSNavigation.kt` | Horizontal filter tabs |
| `CSSectionHeader` | `components/CSContentRow.kt` | Section title + See All |
| `CSContentRow` | `components/CSContentRow.kt` | Horizontal scrollable row |

#### Tokens (NEVER hardcode values — use these)
| Token | File | Examples |
|-------|------|---------|
| `CSTheme.colors.*` | `tokens/CSColors.kt` | `.background`, `.textPrimary`, `.accentPrimary`, `.surface` |
| `CSTheme.typography.*` | `tokens/CSTypography.kt` | `.displaySmall`, `.headlineMedium`, `.bodyMedium`, `.caption` |
| `CSTheme.spacing.*` | `tokens/CSDimensions.kt` | `.xs` (4dp), `.sm` (8dp), `.md` (12dp), `.lg` (16dp), `.xl` (24dp) |
| `CSTheme.sizes.*` | `tokens/CSDimensions.kt` | `.iconSm`, `.iconMd`, `.buttonHeight`, `.posterWidthMd` |
| `CSTheme.shapes.*` | `tokens/CSShapes.kt` | `.xs`, `.sm`, `.md`, `.lg`, `.pill`, `.bottomSheet` |
| `CSTheme.elevation.*` | `tokens/CSDimensions.kt` | `.none`, `.xs`, `.sm`, `.md`, `.lg` |
| `AlphaDefaults.*` | `tokens/CSDimensions.kt` | `.Disabled`, `.Full`, `.Overlay`, `.Frosted` |
| `CSIcons.*` | `tokens/CSIcons.kt` | `.Home`, `.Search`, `.Play`, `.Download`, `.Share`, `.Back` |
| `CSAnimation.*` | `tokens/CSAnimation.kt` | `.Normal`, `.Fast`, `.focusScale()`, `.emphasizedEaseOut()` |
| `CSGradients.*` | `tokens/CSColors.kt` | `.heroBottomScrim`, `.cardOverlay`, `.topFade` |

#### Foundation
| Utility | File | Purpose |
|---------|------|---------|
| `responsiveValue()` | `foundation/WindowSizeClass.kt` | Select value by screen size |
| `WindowSizeClass` | `foundation/WindowSizeClass.kt` | Compact/Medium/Expanded |
| `Modifier.csFocusable()` | `foundation/FocusSystem.kt` | TV focus with scale+ring |
| `CSTheme.isTV` | `theme/CSTheme.kt` | TV mode flag |
| `CSScaffold` | `layout/CSScaffold.kt` | Responsive scaffold |
| `CSGrid` | `layout/CSGrid.kt` | Responsive grid |

#### Screen Layouts (already built)
| Screen | File | State Model |
|--------|------|-------------|
| `HomeScreen` | `screens/home/HomeScreen.kt` | `HomeUiState` |
| `HeroCarousel` | `screens/home/HeroCarousel.kt` | `List<HeroItem>` |
| `MovieDetailScreen` | `screens/detail/MovieDetailScreen.kt` | `MovieDetailUiState` |
| `TvShowDetailScreen` | `screens/detail/TvShowDetailScreen.kt` | `TvShowDetailUiState` |

#### UI State Models (`screens/models.kt`)
- `ContentItem`, `CastMember`, `TrailerItem` — shared
- `HomeUiState`, `HeroItem`, `ContentSection`, `ContentSectionType`
- `DetailUiState`, `MovieDetailUiState`, `TvShowDetailUiState`
- `SeasonInfo`, `EpisodeItem`

### Library Module (`library/src/commonMain/`)

**Existing KMP shared code — wrap, don't rewrite:**

| Class | Purpose |
|-------|---------|
| `MainAPI` | Base class for all content providers |
| `ExtractorApi` / `ExtractorLink` | Video link extraction |
| `CloudStreamClient` | Ktor HTTP client (KMP) |
| `SyncAPI` | Sync provider interface (AniList, MAL, etc.) |
| `SubtitleFile`, `AudioFile` | Media track models |
| `TvType` (enum) | Movie, TvSeries, Anime, etc. (17 types) |
| `DubStatus` (enum) | Sub/Dub status |
| `ShowStatus` (enum) | Ongoing, Completed, etc. |
| `SearchQuality` (enum) | SD, HD, 4K, etc. |
| `HomePageResponse`, `HomePageList` | Home page data |
| `MovieLoadResponse`, `TvSeriesLoadResponse`, `AnimeLoadResponse` | Detail page data |
| `Episode`, `SeasonData` | Episode/season models |
| `Actor`, `ActorData` | Cast data |
| `TrailerData` | Trailer info |
| `SearchResponse` hierarchy | Search result models |
| `M3u8Helper` | HLS utilities |
| `FuzzySearch` | String matching |
| `HlsPlaylistParser` | M3U8 parsing |
| `CryptoHelper` | expect/actual crypto operations |
| `JsEngine` | expect/actual JS evaluation |
| `SubtitleHelper` | Subtitle parsing |

---

## COMPLETE Feature Inventory (from existing Android app)

> **EVERY feature below MUST be implemented.** Before building each one, read the referenced source files to understand the full behavior.

### Feature 1: Home Feed (`:feature:home`)

**Reference**: `app/.../ui/home/`

| Source File | What It Does |
|-------------|-------------|
| `HomeFragment.kt` | Main home screen with carousel and content rows |
| `HomeViewModel.kt` | Loads home page from active providers, manages feed state |
| `HomeParentItemAdapter.kt` | Parent carousel adapter for content sections |
| `HomeChildItemAdapter.kt` | Child item adapter within each section |
| `HomeScrollAdapter.kt` | Scroll optimization |
| `HomeScrollTransformer.kt` | Scroll transformation effects |

**Sub-features to implement:**
- [ ] Hero carousel with auto-rotation and page indicators
- [ ] Multiple content sections (Continue Watching, Trending, Popular, by genre)
- [ ] Content section types: poster rows and landscape rows
- [ ] "See All" navigation for each section
- [ ] Pull-to-refresh
- [ ] Provider-based content loading (multiple providers contribute sections)
- [ ] Shimmer loading states
- [ ] Responsive: phone/tablet/TV layouts

**Reuse from designsystem**: `HomeScreen`, `HeroCarousel`, `CSContentRow`, `CSPosterCard`, `CSLandscapeCard`, `CSHeroCard`, `CSShimmer`

---

### Feature 2: Search (`:feature:search`)

**Reference**: `app/.../ui/search/`

| Source File | What It Does |
|-------------|-------------|
| `SearchFragment.kt` | Full search screen with results grid |
| `SearchViewModel.kt` | Multi-provider search, result aggregation |
| `SearchAdaptor.kt` | Search results list adapter |
| `SearchHistoryAdaptor.kt` | Recent search history display |
| `SearchSuggestionAdapter.kt` | Autocomplete suggestions |
| `SearchSuggestionApi.kt` | Suggestion API integration |
| `SearchHelper.kt` | Search utilities |
| `SearchResultBuilder.kt` | Dynamic result building |
| `SyncSearchViewModel.kt` | Search with sync provider integration |

**Also**: `app/.../ui/quicksearch/QuickSearchFragment.kt` — Quick/jump search dialog

**Sub-features to implement:**
- [ ] Full-text search across all active providers simultaneously
- [ ] Search suggestions/autocomplete
- [ ] Search history (recent searches, clear history)
- [ ] Results grid with poster cards
- [ ] Provider filter chips (search specific providers)
- [ ] Content type filter (Movie, TV, Anime, etc.)
- [ ] Quick search dialog (launched from detail screen)
- [ ] Category browse grid with `CSCategoryTile`
- [ ] Debounced search input
- [ ] Empty state, error state, no results state
- [ ] Responsive: grid columns adapt to screen size

**Reuse from designsystem**: `CSSearchBar`, `CSChip`, `CSCategoryTile`, `CSGrid`, `CSPosterCard`

---

### Feature 3: Content Detail (`:feature:detail`)

**Reference**: `app/.../ui/result/`

| Source File | What It Does |
|-------------|-------------|
| `ResultFragment.kt` | Base result/details screen |
| `ResultFragmentPhone.kt` | Phone-optimized detail layout |
| `ResultFragmentTv.kt` | TV-optimized detail layout |
| `ResultViewModel2.kt` | **Very large** — content loading, episode management, link extraction, watchlist, sync |
| `SyncViewModel.kt` | Sync state (AniList, MAL scores/status) |
| `EpisodeAdapter.kt` | Episode list rendering |
| `ActorAdaptor.kt` | Cast/actor display |
| `ImageAdapter.kt` | Image gallery |
| `SelectAdaptor.kt` | Selection UI |
| `ResultTrailerPlayer.kt` | Inline trailer playback |

**Sub-features to implement:**
- [ ] Movie detail screen (hero, metadata, actions, synopsis, cast, trailers, recommendations)
- [ ] TV show detail screen (hero, metadata, actions, synopsis, tabs: Episodes/More Like This/Trailers, season selector, episode list, cast)
- [ ] Anime detail screen (same as TV show + dub/sub selector, filler indicators, AniList/MAL integration)
- [ ] Link extraction and source selection
- [ ] Episode loading with season/dub grouping
- [ ] Episode sort options (number asc/desc, aired asc/desc)
- [ ] Watch progress per episode (red progress bar)
- [ ] Add to library/watchlist toggle
- [ ] Download episode button
- [ ] Share content
- [ ] Rate content (via sync providers)
- [ ] Inline trailer player
- [ ] Cast/actor avatars with horizontal scroll
- [ ] Recommendation row ("More Like This")
- [ ] Sync provider integration (AniList score, MAL status shown inline)
- [ ] Deep link support (`cloudstreamapp://`)
- [ ] Shimmer loading states
- [ ] Responsive: phone/tablet/TV layouts with constrained content width

**Reuse from designsystem**: `MovieDetailScreen`, `TvShowDetailScreen`, `DetailHero`, `DetailTitleSection`, `DetailActionBar`, `CastSection`, `RecommendationsSection`, `TrailersSection`, `SeasonSelector`, `EpisodeListSection`

---

### Feature 4: Video Player (`:feature:player`)

**Reference**: `app/.../ui/player/`

| Source File | What It Does |
|-------------|-------------|
| `GeneratorPlayer.kt` | **Very large** — main video player with full controls |
| `FullScreenPlayer.kt` | Full-screen player component |
| `AbstractPlayerFragment.kt` | Base player abstraction |
| `CS3IPlayer.kt` | **Very large** — custom ExoPlayer wrapper with extensive features |
| `PlayerGeneratorViewModel.kt` | Player state management, source/episode navigation |
| `PlayerPipHelper.kt` | Picture-in-Picture support |
| `PlayerSubtitleHelper.kt` | Subtitle rendering in player |
| `IPlayer.kt` | Player interface definition |
| `IGenerator.kt` / `LinkGenerator.kt` | Content generator/link extraction interfaces |
| `ExtractorLinkGenerator.kt` | Link extraction pipeline |
| `RepoLinkGenerator.kt` | Repository-based link generation |
| `DownloadFileGenerator.kt` | Playback from downloaded files |
| `PreviewGenerator.kt` | Preview content generation |
| `OfflinePlaybackHelper.kt` | Offline playback support |
| `QualityProfileDialog.kt` | Quality profile selection |
| `SourcePriorityDialog.kt` | Source priority configuration |
| `QualityDataHelper.kt` | Quality/source data management |
| `CustomSubripParser.kt` | SRT subtitle parsing |
| `CustomSubtitleDecoderFactory.kt` | Custom subtitle decoder |
| `SubtitleOffsetItemAdapter.kt` | Subtitle offset adjustment UI |
| `UpdatedMatroskaExtractor.kt` | MKV/Matroska extraction |
| `UpdatedDefaultExtractorsFactory.kt` | Media format extractors |
| `FixedNextRenderersFactory.kt` | Media renderer factory |
| `SSLTrustManager.kt` | SSL certificate handling |
| `Torrent.kt` | Torrent streaming playback |

**Sub-features to implement:**
- [ ] Full video player with play/pause/seek controls
- [ ] Source selection dialog (multiple video sources with quality)
- [ ] Quality selection (SD, HD, FullHD, 4K)
- [ ] Quality profiles (user-configurable priority rules)
- [ ] Source priority ranking
- [ ] Subtitle support (SRT, VTT, ASS formats)
- [ ] Multiple subtitle track selection
- [ ] Subtitle offset/timing adjustment
- [ ] Custom subtitle rendering with styling (outline, background)
- [ ] Audio track selection (multi-language)
- [ ] Playback speed control (0.25x–2x)
- [ ] Aspect ratio modes
- [ ] Video rotation
- [ ] Picture-in-Picture (PiP) mode
- [ ] Chromecast/Google Cast integration
- [ ] Fcast third-party casting
- [ ] Episode navigation (next/previous episode)
- [ ] Resume position tracking and persistence
- [ ] AniSkip integration (skip anime openings/endings/recaps)
- [ ] Filler episode detection
- [ ] HLS (M3U8) adaptive streaming
- [ ] DASH format support
- [ ] MKV/Matroska container support
- [ ] Torrent streaming playback
- [ ] Offline/downloaded content playback
- [ ] Mini controller (floating player control when navigating away)
- [ ] Lock screen / notification controls
- [ ] SSL certificate handling for restricted sources
- [ ] External player integration (VLC, MPV, NextPlayer, JustPlayer, etc.)
- [ ] "Open in browser" / "Copy link" actions
- [ ] Platform-specific: Android=ExoPlayer/Media3, iOS=AVPlayer

---

### Feature 5: Library/Watchlist (`:feature:library`)

**Reference**: `app/.../ui/library/`

| Source File | What It Does |
|-------------|-------------|
| `LibraryFragment.kt` | Saved/bookmarked content with tabs |
| `LibraryViewModel.kt` | Library data management, sorting, filtering |
| `ViewpagerAdapter.kt` | Tab page adapter |
| `PageAdapter.kt` | Content pagination |
| `LoadingPosterAdapter.kt` | Loading animation |

**Sub-features to implement:**
- [ ] Tabbed library view (All, Watching, Completed, Dropped, Plan to Watch)
- [ ] Grid display with poster cards
- [ ] Sort options (alphabetical, recently added, rating)
- [ ] Filter by content type (Movie, Series, Anime)
- [ ] Watch progress indicators on poster cards
- [ ] Remove from library
- [ ] Sync with external providers (AniList, MAL, etc.)
- [ ] Empty state per tab
- [ ] Search within library

---

### Feature 6: Downloads (`:feature:downloads`)

**Reference**: `app/.../ui/download/`, `app/.../utils/downloader/`

| Source File | What It Does |
|-------------|-------------|
| `DownloadFragment.kt` | Download list screen |
| `DownloadChildFragment.kt` | Content within download folder |
| `DownloadViewModel.kt` | Download state management |
| `DownloadAdapter.kt` | Download list rendering |
| `DownloadQueueFragment.kt` | Active download queue |
| `DownloadQueueAdapter.kt` | Queue rendering |
| `DownloadQueueViewModel.kt` | Queue state |
| `DownloadButtonSetup.kt` | Download button component |
| `PieFetchButton.kt` | Pie chart progress button |
| `DownloadManager.kt` | **Very large** — core download engine |
| `DownloadQueueManager.kt` | Queue management |
| `DownloadFileManagement.kt` | File organization |
| `DownloadObjects.kt` | Download data models |
| `DownloadUtils.kt` | Download utilities |
| `VideoDownloadHelper.kt` | Download helpers |

**Also services**: `VideoDownloadService.kt`, `DownloadQueueService.kt`, `VideoDownloadRestartReceiver.kt`, `DownloadFileWorkManager.kt`

**Sub-features to implement:**
- [ ] Download list organized by source/folder
- [ ] Download child view (episodes within a show)
- [ ] Active download queue with progress
- [ ] Pause/resume/cancel individual downloads
- [ ] Multiple concurrent downloads
- [ ] Background download service
- [ ] Download restart on boot
- [ ] Pie chart progress indicator
- [ ] Download complete notification
- [ ] Play downloaded content offline
- [ ] Delete downloaded content
- [ ] Storage usage display
- [ ] Directory picker for download location

---

### Feature 7: Settings (`:feature:settings`)

**Reference**: `app/.../ui/settings/`

| Source File | What It Does |
|-------------|-------------|
| `SettingsFragment.kt` | Main settings hub |
| `SettingsGeneral.kt` | General app settings |
| `SettingsUI.kt` | UI theme/appearance |
| `SettingsPlayer.kt` | Player behavior settings |
| `SettingsProviders.kt` | Provider management |
| `SettingsAccount.kt` | Account/sync settings |
| `SettingsUpdates.kt` | Update management |
| `TestFragment.kt` / `TestViewModel.kt` | Provider testing |
| `DirectoryPicker.kt` | Folder selection |
| `LogcatAdapter.kt` | Debug log viewer |

**Sub-features to implement:**
- [ ] **General settings**: Language, provider languages, DNS-over-HTTPS, preferred media types
- [ ] **UI settings**: Theme (dark/light), layout mode (phone/TV/emulator), card style
- [ ] **Player settings**: Default player, preferred quality, subtitle defaults, playback speed default, resize mode, subtitle encoding
- [ ] **Provider settings**: Active providers, provider language filter, provider testing
- [ ] **Account settings**: Sync provider management (see Auth feature)
- [ ] **Update settings**: Check for updates, pre-release opt-in, changelog display
- [ ] **Subtitle settings**: Default language, font, size, background, encoding
- [ ] **Backup & restore**: Full data export/import
- [ ] **Debug**: Logcat viewer, provider testing framework
- [ ] **About**: Version info, credits, links

---

### Feature 8: Extensions/Plugins (`:feature:extensions`)

**Reference**: `app/.../plugins/`, `app/.../ui/settings/` (Extensions/Plugins fragments)

| Source File | What It Does |
|-------------|-------------|
| `PluginManager.kt` | **Very large** — plugin loading, lifecycle, compatibility |
| `RepositoryManager.kt` | Plugin repository management |
| `Plugin.kt` | Plugin base class wrapper |
| `VotingApi.kt` | Community voting for providers |
| `ExtensionsFragment.kt` | Browse extension repositories |
| `PluginsFragment.kt` | Installed plugins management |
| `PluginsViewModel.kt` | Plugin data management |
| `ExtensionsViewModel.kt` | Extension data management |
| `PluginDetailsFragment.kt` | Plugin detail/info screen |
| `PluginAdapter.kt` | Plugin list adapter |
| `RepoAdapter.kt` | Repository list adapter |

**Sub-features to implement:**
- [ ] Browse extension repositories
- [ ] Search plugins by name
- [ ] Install/update/uninstall plugins
- [ ] Plugin details screen (description, version, author, changelog)
- [ ] Repository management (add/remove repositories)
- [ ] Community voting/rating on providers
- [ ] Plugin compatibility checking
- [ ] Auto-update plugins
- [ ] Plugin enabled/disabled toggle
- [ ] Provider status indicators (working/down/slow/beta)

---

### Feature 9: Auth & Accounts (`:feature:auth`)

**Reference**: `app/.../syncproviders/`, `app/.../ui/account/`

| Source File | What It Does |
|-------------|-------------|
| `AccountManager.kt` | Multi-account management |
| `AccountSelectActivity.kt` | Account/profile selection screen |
| `AccountViewModel.kt` | Account data management |
| `AccountAdapter.kt` | Account list display |
| `AuthAPI.kt` | Authentication interface |
| `AuthRepo.kt` | Auth persistence |
| `SyncAPI.kt` | Sync provider interface |
| `SyncRepo.kt` | Sync data persistence |
| `BackupAPI.kt` | Backup/restore interface |
| `AniListApi.kt` | **Large** — AniList GraphQL integration |
| `MALApi.kt` | MyAnimeList API integration |
| `KitsuApi.kt` | Kitsu API integration |
| `SimklApi.kt` | **Large** — Simkl multi-service tracking |
| `LocalList.kt` | Local watchlist (no sync) |

**Subtitle providers** (also in syncproviders/):
| Source File | What It Does |
|-------------|-------------|
| `SubtitleAPI.kt` | Subtitle search interface |
| `SubtitleRepo.kt` | Subtitle persistence |
| `OpenSubtitlesApi.kt` | OpenSubtitles integration |
| `Subdl.kt` | Subdl subtitle source |
| `SubSource.kt` | SubSource subtitle source |
| `Addic7ed.kt` | Addic7ed subtitle source |

**Sub-features to implement:**
- [ ] Multi-account support with profiles (7 avatar variants)
- [ ] Account selection screen with visual profiles
- [ ] AniList OAuth login + anime tracking (score, status, episodes)
- [ ] MyAnimeList OAuth login + anime tracking
- [ ] Kitsu login + anime tracking
- [ ] Simkl login + multi-service tracking (movies, TV, anime)
- [ ] Local list management (non-synced watchlist)
- [ ] Watch progress sync across devices (via sync providers)
- [ ] Subtitle provider management (OpenSubtitles, Subdl, SubSource, Addic7ed)
- [ ] Backup/restore all data
- [ ] Biometric authentication (fingerprint/face/PIN lock)
- [ ] Per-account preferences

---

### Feature 10: Onboarding (`:feature:onboarding`)

**Reference**: `app/.../ui/setup/`

| Source File | What It Does |
|-------------|-------------|
| `SetupFragmentLanguage.kt` | Language selection |
| `SetupFragmentProviderLanguage.kt` | Provider language preferences |
| `SetupFragmentExtensions.kt` | Initial extension installation |
| `SetupFragmentMedia.kt` | Media type preferences (movies, anime, etc.) |
| `SetupFragmentLayout.kt` | Layout mode selection (phone/TV/emulator) |

**Sub-features to implement:**
- [ ] Step 1: App language selection
- [ ] Step 2: Provider language preferences (which languages for content)
- [ ] Step 3: Extension/plugin installation from repositories
- [ ] Step 4: Media type preferences (which types to show)
- [ ] Step 5: Layout mode selection
- [ ] Progress indicator across steps
- [ ] Skip/back navigation
- [ ] First-run detection and redirect

---

### Feature 11: Subtitles (`:feature:subtitles`)

**Reference**: `app/.../ui/subtitles/`, `app/.../subtitles/`

| Source File | What It Does |
|-------------|-------------|
| `SubtitlesFragment.kt` | Subtitle settings screen |
| `ChromecastSubtitlesFragment.kt` | Chromecast subtitle settings |
| `AbstractSubProvider.kt` | Subtitle provider base |
| `AbstractSubtitleEntities.kt` | Subtitle data classes |

**Sub-features to implement:**
- [ ] Subtitle language selection
- [ ] Subtitle font/size/color customization
- [ ] Subtitle background styling
- [ ] Subtitle encoding selection
- [ ] Chromecast-specific subtitle settings
- [ ] Subtitle offset adjustment
- [ ] Auto-select subtitle by language preference

---

### Feature 12: Network & Security (`:core:network`)

**Reference**: `app/.../network/`

| Source File | What It Does |
|-------------|-------------|
| `CloudflareKiller.kt` | Cloudflare protection bypass |
| `DdosGuardKiller.kt` | DDoS-Guard bypass |
| `RequestsHelper.kt` | HTTP request utilities |
| `DohProviders.kt` | DNS-over-HTTPS providers |
| `CastHelper.kt` | Chromecast integration |

**Sub-features to implement:**
- [ ] Cloudflare bypass (WebView-based challenge solving)
- [ ] DDoS-Guard bypass
- [ ] DNS-over-HTTPS (DoH) with multiple providers
- [ ] SSL/TLS certificate handling for restricted sources
- [ ] Request interceptors for custom headers/cookies
- [ ] Chromecast/Google Cast framework integration

---

### Feature 13: External Player Actions (`:feature:player`)

**Reference**: `app/.../actions/`, `app/.../actions/temp/`

| Source File | What It Does |
|-------------|-------------|
| `VideoClickAction.kt` | Base action interface + registry |
| `OpenInAppAction.kt` | Open in external app |
| `AlwaysAskAction.kt` | Action picker dialog |
| `PlayInBrowserAction.kt` | Play in browser |
| `CopyClipboardAction.kt` | Copy link to clipboard |
| `ViewM3U8Action.kt` | View M3U8 manifest |
| `VlcPackage.kt` | VLC player integration |
| `MpvPackage.kt` / `MpvKtPackage.kt` | MPV player integration |
| `NextPlayerPackage.kt` | NextPlayer integration |
| `JustPlayerPackage.kt` | Just Player integration |
| `WebVideoCastPackage.kt` | Web Video Cast integration |
| `FcastAction.kt` / `FcastManager.kt` | Fcast casting |
| `Aria2Package.kt` / `BiglyBTPackage.kt` / `LibreTorrentPackage.kt` | Torrent clients |

**Sub-features to implement:**
- [ ] Configurable default video action (internal player, external player, always ask)
- [ ] VLC integration
- [ ] MPV integration (multiple variants)
- [ ] NextPlayer, JustPlayer integrations
- [ ] Web Video Cast integration
- [ ] Fcast casting protocol
- [ ] Torrent client integrations (Aria2, BiglyBT, LibreTorrent)
- [ ] Copy link to clipboard
- [ ] Open in browser
- [ ] "Always ask" action picker dialog

---

### Feature 14: TV & Android TV (`:core:common`)

**Reference**: scattered across `app/`

| Source File | What It Does |
|-------------|-------------|
| `ControllerActivity.kt` | TV remote controller interface |
| `ResultFragmentTv.kt` | TV-optimized detail layout |
| `TvChannelUtils.kt` | Android TV channels & Watch Next |

**Sub-features to implement:**
- [ ] TV-optimized layouts for all screens (using `CSTheme.isTV` and `responsiveValue`)
- [ ] D-pad navigation with focus management (using `Modifier.csFocusable()`)
- [ ] Android TV Watch Next integration
- [ ] TV channel creation for content providers
- [ ] Fire TV compatibility
- [ ] TV remote controller support
- [ ] Side rail navigation instead of bottom bar

---

### Feature 15: Utilities & Cross-cutting (`:core:common`, `:core:datastore`)

**Reference**: `app/.../utils/`

| Source File | What It Does |
|-------------|-------------|
| `DataStoreHelper.kt` | **Very large** — all persistent preferences and watch state |
| `DataStore.kt` | Key-value storage abstraction |
| `BackupUtils.kt` | Full data backup/restore |
| `UIHelper.kt` | **Very large** — UI utilities |
| `AppContextUtils.kt` | **Very large** — context utilities |
| `InAppUpdater.kt` | In-app update system |
| `BiometricAuthenticator.kt` | Biometric authentication |
| `AniSkip.kt` | Anime opening/ending skip API |
| `FillerEpisodeCheck.kt` | Filler episode detection API |
| `SubtitleUtils.kt` | Subtitle utilities |
| `SyncUtil.kt` | Sync utilities |
| `ImageUtil.kt` | Image utilities |
| `TextUtil.kt` | Text utilities |
| `IntentHelpers.kt` | Deep link/intent handling |
| `PowerManagerAPI.kt` | Battery optimization handling |
| `PackageInstaller.kt` | APK installation |
| `TvChannelUtils.kt` | TV channel management |
| `SingleSelectionHelper.kt` | Selection dialog helpers |
| `SnackbarHelper.kt` | Snackbar/toast management |
| `Event.kt` | Event bus system |

**Sub-features to implement:**
- [ ] Key-value datastore (KMP — expect/actual for Android SharedPrefs, iOS UserDefaults)
- [ ] Watch state persistence (position, duration, watch status per video)
- [ ] Full data backup & restore (export/import all settings, watchlist, history)
- [ ] In-app update checker (GitHub releases)
- [ ] APK download & installation
- [ ] Biometric authentication guard
- [ ] AniSkip API integration (skip timestamps)
- [ ] Filler episode detection (animefillerlist.com)
- [ ] Deep link handling (7 URL schemes: cloudstreamplayer://, cloudstreamapp://, cloudstreamrepo://, csshare://, cloudstreamsearch://, cloudstreamcontinuewatching://, magnet://)
- [ ] QR code sharing
- [ ] Battery optimization bypass
- [ ] Event bus for cross-feature communication
- [ ] Easter egg (benene counter → monkey screen)

---

## Design Inspirations

Reference screenshots are in `inspirations/` (366 images from Netflix, HBO Max, Apple TV+, YouTube, etc.)

| Directory | Count | Content |
|-----------|-------|---------|
| `01-home-feed/` | 82 | Hero carousels, content rows, recommendations |
| `02-content-detail/` | 35 | Detail pages, episode lists, cast sections |
| `03-video-player/` | 22 | Player controls, subtitles, PiP |
| `04-search-browse/` | 26 | Search UX, category grids, filters |
| `05-subscription-paywall/` | 6 | Plan selection, pricing |
| `06-onboarding-auth/` | 19 | Welcome screens, sign-up flows |
| `07-profile-settings/` | 21 | Profile selection, settings pages |
| `08-watchlist-downloads/` | 26 | My List, downloads, offline |
| `09-tablet-web/` | 50 | Tablet/TV wide-screen adaptations |
| `10-flows/` | 79 | Complete user journeys |

Full design system spec: `inspirations/DESIGN_SYSTEM.md` (836 lines)

---

## Team Agent Configuration

Use Claude Code agent teams for parallel development. Each agent should:
1. **Read CLAUDE.md first** for project conventions
2. **Read the existing Android implementation** of the feature they're building
3. **Search existing code** before creating anything new
4. **Use design system tokens** — never hardcode values
5. **Follow the architecture** — domain → data → presentation layers
6. **Write tests** alongside implementation

### Agent Roles

```yaml
feature-engineer:
  role: "Implements features end-to-end following clean architecture"
  instructions: |
    1. Read CLAUDE.md for conventions
    2. Read the EXISTING Android implementation of the feature in app/src/main/java/
    3. Understand ALL sub-features and edge cases from the existing code
    4. Search designsystem/ for existing components before creating UI
    5. Search library/ for existing API models before creating domain models
    6. Create: domain models → use cases → repository interfaces → data layer → UI state → screen
    7. Use CSTheme tokens for all values, CSIcons for icons
    8. Add unit tests for ViewModels and UseCases
    9. Update task list when starting/completing work
    10. Ensure 100% feature parity with existing Android implementation

code-reviewer:
  role: "Reviews code for SOLID violations, hardcoded values, missing features, and test coverage"
  instructions: |
    Review checklist:
    - [ ] No hardcoded strings, colors, dimensions, or icons
    - [ ] Uses CSTheme.colors/typography/spacing/shapes/sizes
    - [ ] Uses CSIcons instead of Icons.Default
    - [ ] Uses AlphaDefaults for opacity values
    - [ ] Domain models are separate from API and UI models
    - [ ] Repository interfaces in domain, implementations in data
    - [ ] UseCases have single responsibility
    - [ ] ViewModels expose StateFlow<UiState>, not LiveData
    - [ ] Screen composables are stateless (state hoisted to ViewModel)
    - [ ] All public functions have tests
    - [ ] No service locators or static singletons
    - [ ] Mappers exist between layers
    - [ ] Feature parity: compare against existing Android implementation
    - [ ] No missing sub-features or edge cases

architect:
  role: "Designs module structure and data flow before implementation"
  instructions: |
    Before any feature work:
    1. READ the existing Android implementation in app/ THOROUGHLY
    2. List EVERY sub-feature, dialog, adapter, and edge case
    3. Map the data flow: API → Repository → UseCase → ViewModel → Screen
    4. Identify which library/ models to reuse vs wrap
    5. Define the module boundary (what goes in :feature:X vs :core:X)
    6. List all files to create with their responsibilities
    7. Document the approach in a plan
    8. Ensure NO feature is missed from the existing implementation
```

---

## Implementation Workflow

### For each feature:

```
1. READ     → Read the EXISTING Android implementation thoroughly
2. PLAN     → Architect agent designs the feature structure, lists ALL sub-features
3. DOMAIN   → Define domain models, repository interfaces, use cases
4. DATA     → Implement repositories, mappers, data sources
5. UI       → Build screen using designsystem components + ViewModel
6. TEST     → Unit tests for ViewModel, UseCases, Mappers
7. REVIEW   → Code reviewer agent checks conventions AND feature parity
8. SCREENSHOT → Run Roborazzi tests to verify layouts
```

### Task Tracking

Use Claude Code tasks (`TaskCreate`, `TaskUpdate`, `TaskList`) to track progress:
- Create tasks at the start of each feature (one per sub-feature)
- Mark `in_progress` when starting
- Mark `completed` when done
- Update memory with key decisions

### Resume Protocol

When resuming a session:
1. Read `CLAUDE.md` for project conventions
2. Run `TaskList` to see current progress
3. Check memory at `.claude/projects/-Volumes-ExtStorage-Projects-StreamingApp-cloudstream/memory/`
4. Run `git log --oneline -10` to see recent commits
5. Read this document's "Current Progress" section
6. Continue from the last incomplete task

---

## Current Progress

> **Update this section at the end of each session**

### Completed
- [x] Design system module (`designsystem/`) with all components, tokens, theme
- [x] Screen layouts: Home, Movie Detail, TV Show Detail with shimmer states
- [x] Responsive adaptations for phone/tablet/TV (constrained content widths)
- [x] Roborazzi screenshot tests (12 tests across 3 form factors)
- [x] Android Studio previews for all components and screens
- [x] Library module KMP migration (network, extractors, plugins)
- [x] CLAUDE.md with project conventions
- [x] INITIAL_PROMPT.md with full feature inventory

### In Progress
- [ ] None currently

### Not Started
- [ ] Module scaffold (`:core:*`, `:feature:*` Gradle modules)
- [ ] Navigation graph with route definitions and deep links
- [ ] Feature 1: Home Feed
- [ ] Feature 2: Search
- [ ] Feature 3: Content Detail
- [ ] Feature 4: Video Player
- [ ] Feature 5: Library/Watchlist
- [ ] Feature 6: Downloads
- [ ] Feature 7: Settings (all sub-settings)
- [ ] Feature 8: Extensions/Plugins
- [ ] Feature 9: Auth & Accounts (all sync providers)
- [ ] Feature 10: Onboarding
- [ ] Feature 11: Subtitles
- [ ] Feature 12: Network & Security
- [ ] Feature 13: External Player Actions
- [ ] Feature 14: TV & Android TV
- [ ] Feature 15: Utilities & Cross-cutting
- [ ] Integration tests
- [ ] iOS app target

### Key Decisions Made
- Dark-first theme inspired by Netflix/HBO Max
- Compose Multiplatform for shared UI
- Ktor for networking (already migrated in library/)
- kotlinx.serialization for JSON (already migrated)
- Roborazzi for screenshot testing
- responsiveValue() for phone/tablet/TV adaptations
- 100% feature parity with existing Android app (no exceptions)

---

## Quick Start Commands

```bash
# Build designsystem module
./gradlew :designsystem:assembleDebug

# Run screenshot tests
./gradlew :designsystem:recordRoborazziDebug

# Run unit tests
./gradlew :designsystem:testDebugUnitTest

# Full project build
./gradlew assembleDebug
```
