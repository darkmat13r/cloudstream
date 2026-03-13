# CloudStream Design Inspirations

Curated reference library from major streaming/OTT platforms via [Refero.design](https://refero.design).

## Collection Summary

| Category | Count | Description |
|----------|-------|-------------|
| `01-home-feed/` | 82 | Home screens, hero banners, content carousels, recommendations |
| `02-content-detail/` | 35 | Movie/show detail pages, episode lists, season selectors |
| `03-video-player/` | 22 | Player controls, playback speed, subtitles, PiP |
| `04-search-browse/` | 26 | Search bars, category grids, genre browsing, filters |
| `05-subscription-paywall/` | 6 | Plan selection, pricing cards, feature comparison |
| `06-onboarding-auth/` | 19 | Welcome screens, sign-up/sign-in, onboarding flows |
| `07-profile-settings/` | 21 | Profile selection, settings pages, account management |
| `08-watchlist-downloads/` | 26 | My List, downloads, continue watching, offline content |
| `09-tablet-web/` | 50 | Web/tablet layouts, wider screen adaptations |
| `10-flows/` | 79 steps | Complete user flows (onboarding, browsing, auth) |
| **Total** | **366** | |

## User Flows (10-flows/)

| Folder | App | Steps | Description |
|--------|-----|-------|-------------|
| `netflix_10554_new-account-signup-and-onboarding/` | Netflix | 20 | Full signup: email, password, profile, preferences |
| `netflix_10557_email-code-sign-in-to-locked-profile/` | Netflix | 12 | Email code auth + profile PIN unlock |
| `netflix_10567_profile-selection-and-creation/` | Netflix | 4 | Add/edit profiles with avatar |
| `netflix_10590_discover-and-play-movie/` | Netflix | 2 | Home browse to movie detail overlay |
| `netflix_10562_title-blocking/` | Netflix | 3 | Search and block titles from profile |
| `hbo-max_10274_sign-in-and-profile-unlock/` | HBO Max | 9 | Sign-in, password/code, profile PIN |
| `hbo-max_10284_add-series-to-my-list/` | HBO Max | 3 | Quick add via bottom sheet |
| `hbo-max_10286_adult-animation-browsing/` | HBO Max | 3 | Category browse with sort + grid |
| `hbo-max_10288_remove-saved-item/` | HBO Max | 3 | Remove from My List flow |
| `youtube_2611_save-video-to-playlist-from-mix/` | YouTube | 3 | Save video via overflow menu |
| `youtube_2619_create-playlist-and-save-video/` | YouTube | 5 | Create playlist + privacy selector |
| `youtube_2632_pause-watch-history/` | YouTube | 4 | History management + pause toggle |

## Apps Covered

- **Netflix** - Dark theme, hero auto-play, bottom sheet detail, red accent
- **HBO Max** - Cinematic dark UI, frosted glass cards, condensed typography
- **Apple TV+** - Dual light/dark, blue accent, 5-tab nav, system fonts
- **YouTube** - Light default, red brand, card feed layout
- **Crunchyroll** - Dark theme, orange accent, anime-focused
- **MasterClass** - Dark premium, instructor-focused cards
- **Showcase** - Film tracking, timeline filmography
- **Luminary** - Podcast streaming, dark navy, cyan accent

## Documents

- [`DESIGN_SYSTEM.md`](DESIGN_SYSTEM.md) - Comprehensive design system with colors, typography, spacing, components, and recommendations (836 lines)
- [`manifest.json`](manifest.json) - Machine-readable manifest of all downloaded screens with metadata

## Sources

All screens sourced from [Refero.design](https://refero.design) - a curated library of real product screenshots and user flows.
