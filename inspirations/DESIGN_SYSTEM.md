# CloudStream Design System -- Streaming Platform Inspirations

Compiled from analysis of 150+ screens across Netflix, HBO Max, Apple TV+, YouTube, Disney+, Hulu, Crunchyroll, Paramount+, Peacock, and other major streaming platforms.

---

## 1. Color System

### Dark Theme (Primary)

All major streaming platforms (Netflix, HBO Max, Disney+, Apple TV+ dark mode) default to a dark-first design. The dark palette is the dominant and recommended approach for CloudStream.

#### Background Colors

| Token                  | Hex / Value                         | Usage                                      | Observed In                   |
|------------------------|-------------------------------------|---------------------------------------------|-------------------------------|
| `background`           | `#000000`                           | Primary background, full-bleed areas        | Netflix, HBO Max, Disney+     |
| `background.elevated`  | `#0A0A0A` -- `#0B0B0B`              | Slightly elevated surface, subtle depth     | HBO Max, Netflix              |
| `surface`              | `#121212`                           | Cards, elevated panels, bottom sheets       | Netflix, HBO Max, MasterClass |
| `surface.secondary`    | `#1A1A1A` -- `#1F1F1F`              | Episode cards, dropdown backgrounds         | Netflix                       |
| `surface.tertiary`     | `#222222` -- `#2B2B2B`              | Chips, pill selectors, tab backgrounds      | Netflix, HBO Max, Carbon      |
| `surface.quaternary`   | `#2E2E2E` -- `#333333`              | Unselected chip bg, mini-player bar         | HBO Max, Netflix, Spotify     |
| `surface.overlay`      | `rgba(0,0,0,0.60)`                  | Scrim behind modals/bottom sheets           | Netflix, HBO Max              |
| `surface.frosted`      | `rgba(20,20,20,0.55)`               | Frosted-glass subscription cards            | HBO Max                       |

#### Text Colors

| Token                  | Hex / Value                         | Usage                                      | Observed In                   |
|------------------------|-------------------------------------|---------------------------------------------|-------------------------------|
| `text.primary`         | `#FFFFFF`                           | Headings, titles, active labels             | All platforms                 |
| `text.secondary`       | `#B3B3B3` -- `#BDBDBD`              | Descriptions, synopsis, metadata            | Netflix, HBO Max              |
| `text.tertiary`        | `#8E8E93` -- `#9A9A9A`              | Timestamps, durations, muted labels         | Apple TV, Netflix, HBO Max    |
| `text.quaternary`      | `#666666` -- `#7A7A7A`              | Inactive tabs, disabled text                | Netflix, HBO Max              |
| `text.onAccent`        | `#000000`                           | Text on accent-colored buttons              | Netflix (on yellow badges)    |
| `text.link`            | `#007AFF`                           | Interactive links, "See More"               | Apple TV, Apple Podcasts      |

#### Accent / Brand Colors

| Token                  | Hex / Value                         | Usage                                      | Observed In                   |
|------------------------|-------------------------------------|---------------------------------------------|-------------------------------|
| `accent.primary`       | `#E50914`                           | Netflix red -- progress bars, badges, CTAs  | Netflix                       |
| `accent.blue`          | `#007AFF`                           | AirPlay highlights, active nav, CTAs        | Apple TV                      |
| `accent.purple`        | `#9B51E0`                           | "Exclusive" badge, special labels           | Netflix, HBO Max              |
| `accent.yellow`        | `#FFD400` -- `#FFEB3B`              | Maturity rating badges (e.g., "12+")        | Netflix                       |
| `accent.gold`          | `#E3D68B`                           | Section titles, category headers            | Showcase                      |
| `accent.cyan`          | `#00AAFF`                           | "Added" button, notification toggles        | Luminary                      |
| `accent.green`         | `#2ECC71` (estimated)               | Download complete, success states           | Netflix, HBO Max              |

#### Dividers & Borders

| Token                  | Hex / Value                         | Usage                                      |
|------------------------|-------------------------------------|---------------------------------------------|
| `divider`              | `#1F1F1F` -- `#2E2E2E`              | Subtle separator lines between sections     |
| `border.card`          | `#2B2B2B`                           | Faint card borders on dark surfaces         |
| `border.chip`          | `#3A3A3A`                           | Unselected chip/step indicator outlines     |
| `border.selected`      | `#6E6E6E` -- `#8A8A8A`              | Selected pill outlines                      |

#### Gradient Patterns

Gradients are used extensively across streaming UIs for hero images, overlays, and depth:

| Pattern                                 | CSS Approximation                                                       | Usage                           |
|-----------------------------------------|-------------------------------------------------------------------------|---------------------------------|
| Hero bottom scrim                       | `linear-gradient(to bottom, transparent, rgba(0,0,0,0.85))`            | Text legibility over hero image |
| Page background vignette                | `linear-gradient(180deg, #0B0B0D 0%, #111315 100%)`                    | HBO Max page depth              |
| Card depth gradient                     | `linear-gradient(180deg, #262626 0%, #1F1F1F 100%)`                    | Netflix bottom sheet            |
| Cinematic hero (HBO Max)                | `radial-gradient(ellipse, #050405, #2B0F0F, #441515)`                  | Warm maroon dramatic bg         |
| Frosted card inner                      | `rgba(31,32,35,0.85)` with backdrop blur                               | HBO Max subscription cards      |
| Apple TV+ promotional                   | `linear-gradient(135deg, purple, pink, orange)`                         | MLS Season Pass banner          |

### Light Theme (Secondary)

Light theme is primarily used by YouTube (default) and Apple TV+ (system default). It is secondary for streaming apps.

| Token                  | Hex / Value                         | Usage                                      | Observed In                   |
|------------------------|-------------------------------------|---------------------------------------------|-------------------------------|
| `background`           | `#FFFFFF`                           | Primary background                         | YouTube, Apple TV (light)     |
| `surface`              | `#F0F0F0` -- `#F2F2F2`              | Search fields, card backgrounds             | YouTube, Apple Podcasts       |
| `text.primary`         | `#000000`                           | Headings, titles                            | YouTube, Apple TV (light)     |
| `text.secondary`       | `#606060`                           | Subtitles, artist names                     | YouTube                       |
| `text.tertiary`        | `#B0B0B0`                           | Placeholders, muted metadata               | YouTube, Apple Podcasts       |
| `text.link`            | `#065FD4` (estimated)               | "See more" links                            | YouTube                       |
| `accent.red`           | `#FF0000`                           | YouTube brand, subscribe button             | YouTube                       |
| `divider`              | `#E0E0E0`                           | Light separators, placeholder outlines      | YouTube, Apple TV             |

---

## 2. Typography

### Font Families

| Platform       | Primary Font                          | Fallback / System                    |
|----------------|---------------------------------------|--------------------------------------|
| Netflix        | Netflix Sans (proprietary)            | Helvetica Neue, SF Pro Display       |
| HBO Max        | Geometric sans-serif                  | Proxima Nova, Helvetica Neue, Inter  |
| Apple TV+      | SF Pro Display / SF Pro Text          | System default                       |
| YouTube        | Roboto (Android) / SF Pro (iOS)       | System sans-serif                    |
| Disney+        | Avenir (estimated)                    | System sans-serif                    |
| **CloudStream**| **System sans-serif recommended**     | **Roboto (Android), SF Pro (iOS)**   |

### Type Scale (Mobile)

Extracted from actual screen measurements across Netflix, HBO Max, Apple TV+, and YouTube:

| Token               | Size (px/pt) | Weight   | Usage                                       |
|----------------------|-------------|----------|----------------------------------------------|
| `display.large`      | 44--48      | 800--900 | Hero show titles, onboarding headlines        |
| `display.medium`     | 34--36      | 700--800 | HBO Max hero headlines, section features      |
| `display.small`      | 28--32      | 700      | Large section headings, profile names         |
| `headline.large`     | 24--26      | 700      | Page titles ("Choose Your Plan")              |
| `headline.medium`    | 20--22      | 600--700 | Section headers, "My Netflix", nav titles     |
| `headline.small`     | 18          | 600      | Subsection titles, card titles                |
| `title.large`        | 16--17      | 600      | Episode titles, list item headings            |
| `title.medium`       | 14--15      | 500--600 | Button labels, chip text, tab labels          |
| `body.large`         | 16          | 400      | Synopsis text, descriptions, feature bullets  |
| `body.medium`        | 14          | 400      | Card descriptions, metadata                  |
| `body.small`         | 13          | 400      | Episode descriptions, secondary info         |
| `caption`            | 12          | 400--500 | Timestamps, durations, badge text, metadata  |
| `overline`           | 10--11      | 500--600 | Category labels, "NEW EPISODE", uppercase     |

### Weight Hierarchy

| Weight    | Value | Usage                                           |
|-----------|-------|-------------------------------------------------|
| Black     | 900   | HBO Max onboarding headlines, condensed display  |
| Extra Bold| 800   | Netflix hero titles, heavy display copy          |
| Bold      | 700   | Section headings, button labels, titles          |
| Semibold  | 600   | Subtitles, navigation titles, active tabs        |
| Medium    | 500   | Chip text, tab labels, metadata                  |
| Regular   | 400   | Body text, descriptions, synopsis                |

### Line Heights

| Text Type         | Line Height Ratio | Example                              |
|-------------------|-------------------|--------------------------------------|
| Display / Hero    | 1.0--1.1          | 44px text / 48px line-height         |
| Headline          | 1.2--1.3          | 22px text / 28px line-height         |
| Body              | 1.4--1.5          | 16px text / 22--24px line-height     |
| Caption           | 1.3--1.4          | 12px text / 16px line-height         |

---

## 3. Spacing & Layout

### Grid System

| Property                 | Mobile (Phone)           | Tablet                         |
|--------------------------|--------------------------|--------------------------------|
| Screen width             | 375--390px               | 768--1024px                    |
| Content padding (horizontal) | 16px                 | 24--32px                       |
| Content max-width        | Full width               | 720--840px centered            |
| Columns (grid)           | 2--3 (posters)           | 4--6 (posters)                 |

### Spacing Scale

Derived from padding and gap values observed across platforms:

| Token       | Value  | Usage                                                     |
|-------------|--------|-----------------------------------------------------------|
| `space.xxs` | 4px    | Icon-to-label gap, inline spacing                         |
| `space.xs`  | 6px    | Tight padding (badge internal)                            |
| `space.sm`  | 8px    | Card internal top/bottom, chip padding                    |
| `space.md`  | 12px   | Card gaps, icon button padding, tabbar top padding        |
| `space.lg`  | 16px   | Section horizontal padding, hero outer margin, card gaps  |
| `space.xl`  | 20px   | Vertical spacing between cards, section gaps              |
| `space.xxl` | 24px   | Section vertical spacing, large internal padding          |
| `space.3xl` | 32px   | Between major sections, page top/bottom padding           |
| `space.4xl` | 48px   | Hero-to-content spacing, large section breaks             |

### Content Padding Specifics

| Element                  | Horizontal Padding | Vertical Padding      |
|--------------------------|--------------------|-----------------------|
| Screen edges             | 16px               | --                    |
| Card internal            | 12--16px           | 12--16px              |
| Bottom sheet             | 16--18px           | 16--24px              |
| Section header           | 16px               | 8--12px top, 4px bottom |
| Episode card             | 12--14px           | 10--12px              |
| Subscription card        | 16--20px           | 20--24px              |
| Tab bar                  | 12px top           | safe area bottom      |

### Safe Areas

| Area                     | Value                                                    |
|--------------------------|----------------------------------------------------------|
| Status bar               | System-managed (44px on notched, 20px on non-notched)    |
| Bottom navigation        | 56px + safe area inset                                   |
| Hero bleed               | Full-bleed (edge to edge), content 16px inset            |

---

## 4. Component Library

### Navigation

#### Bottom Tab Bar

| Property               | Value / Pattern                                          | Platform Reference          |
|------------------------|----------------------------------------------------------|-----------------------------|
| Height                 | 49--56px (plus safe area)                                | All platforms               |
| Background             | Same as page bg or translucent dark                      | Netflix, HBO Max, Apple TV  |
| Icon size              | 24--28px                                                 | All platforms               |
| Label font size        | 10--11px                                                 | All platforms               |
| Active state           | White filled icon + white label (dark theme)             | Netflix, HBO Max            |
| Active state (Apple)   | Blue (#007AFF) filled icon + blue label                  | Apple TV                    |
| Inactive state         | Gray (#9B9B9B) outline icon + gray label                 | Netflix, HBO Max            |
| Tab count              | 5 tabs typical                                           | All platforms               |

Common tab items observed:
- **Netflix**: Home, Games, New & Hot, My Netflix, (Search via icon)
- **HBO Max**: Home, Bookmarks, Downloads, Search, Profile
- **Apple TV+**: Home, Apple TV+, Store, Library, Search
- **YouTube**: Home, Shorts, (+) Create, Subscriptions, Library
- **Disney+**: Home, Search, Downloads, Profile

#### Top Navigation Bar

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Height                 | 44--56px                                                 |
| Background             | Transparent or matches page background                   |
| Title                  | Centered or left-aligned, 17--20px bold white            |
| Back button            | Left chevron icon, white, 28px tap target                |
| Right actions          | 1--3 icon buttons (search, share, notifications, profile)|
| Logo placement         | Left-aligned (HBO Max, YouTube), 36px height             |

#### Category Tabs (Horizontal)

| Property               | Value / Pattern                                          | Platform Reference          |
|------------------------|----------------------------------------------------------|-----------------------------|
| Style                  | Rounded pills / capsules                                 | YouTube, Netflix            |
| Active pill             | Black fill, white text (light) / White fill, black text  | YouTube                     |
| Inactive pill           | Gray fill (#F0F0F0 light, #2E2E2E dark), muted text     | YouTube, HBO Max            |
| Active underline        | Red underline bar, 2--3px thick                          | Netflix, MasterClass        |
| Tab row                 | Horizontally scrollable, 16px leading padding            | All platforms               |
| Tab height              | 32--36px                                                 | All platforms               |
| Tab horizontal padding  | 12--16px                                                 | All platforms               |

### Cards & Tiles

#### Hero / Featured Content Card

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Aspect ratio           | 16:9 (landscape hero) or ~2:3 (portrait poster)         |
| Height                 | 35--40% of viewport (hero), 70% for tall poster          |
| Corner radius          | 0--4px (full-bleed) or 8--12px (inset card)              |
| Bottom gradient        | Linear gradient from transparent to rgba(0,0,0,0.85)     |
| Title overlay          | Large display text, white, heavy weight, positioned at bottom-left or centered |
| Metadata overlay       | Small caption text below title (genre, year, rating)     |
| CTA overlay            | "Play" or "Watch Now" pill button                        |
| Auto-play behavior     | Hero video auto-plays with mute toggle (Netflix, HBO Max)|
| Progress indicator     | Thin red bar at bottom edge (Netflix: 4px, #E50914)      |

#### Horizontal Scroll Content Row

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Section header          | Bold 18--20px, left-aligned, with optional "See All" >   |
| Card width (poster)    | 100--120px (2:3 ratio)                                   |
| Card width (landscape) | 160--180px (16:9 ratio)                                  |
| Card gap               | 8--12px                                                  |
| Row height             | Card height + 40--50px (title + metadata below)          |
| Leading padding        | 16px (matches screen edge padding)                       |
| Peek                   | Next card visible ~20--30px to hint scrollability        |
| Title below card       | 13--14px, medium weight, 1--2 lines, ellipsis truncation |
| Subtitle below card    | 12px, regular weight, muted gray                         |

#### Grid Layouts

| Layout Type    | Columns (Mobile) | Card Ratio | Gap    | Usage                          |
|----------------|------------------|------------|--------|--------------------------------|
| Poster grid    | 3                | 2:3        | 8px    | Browse, "More Like This"       |
| Landscape grid | 2                | 16:9       | 8--12px| Search results, categories     |
| Square grid    | 2--3             | 1:1        | 8--12px| Profile avatars, genre tiles   |
| Category tiles | 2                | ~3:2       | 8--12px| Search category grid           |

#### Card Corner Radii

| Element                | Radius                                                   |
|------------------------|----------------------------------------------------------|
| Hero card (full-bleed) | 0px                                                      |
| Content poster         | 4--6px                                                   |
| Episode thumbnail      | 6--8px                                                   |
| Category tile          | 8--12px                                                  |
| Bottom sheet           | 12--18px (top corners only)                              |
| Subscription card      | 14--20px                                                 |
| Pill buttons           | 999px (fully rounded)                                    |
| Search field           | 8--12px                                                  |

#### Overlay Text / Gradient Treatments

- **Bottom-anchored gradient**: Most common. Linear gradient from transparent at 50% to near-black at 100%, with white title text placed in the opaque zone.
- **Full scrim**: Semi-transparent dark overlay (rgba(0,0,0,0.40--0.60)) over entire card for play button or metadata overlays.
- **Vignette**: Radial gradient darkening edges, used by HBO Max for cinematic poster backgrounds.
- **Top fade**: Subtle top gradient ensuring status bar / navigation readability over hero images.

### Video Player

#### Player Controls Layout

| Element                  | Position / Style                                         | Platform Reference          |
|--------------------------|----------------------------------------------------------|-----------------------------|
| Close (X) button         | Top-left, white icon on semi-transparent circle          | Netflix, Apple TV, HBO Max  |
| PiP button               | Top-left, next to close                                  | Apple TV                    |
| AirPlay/Cast             | Top-right or top area, blue when active (#007AFF)        | Apple TV, YouTube           |
| Volume/Speaker           | Top-right                                                | Apple TV                    |
| Rewind 10s               | Center-left of playback area, ~44px tap target           | Apple TV, Netflix           |
| Play/Pause               | Center, large icon (56--64px)                            | All platforms               |
| Forward 10s              | Center-right of playback area                            | Apple TV, Netflix           |
| Settings gear            | Top-right (YouTube)                                      | YouTube                     |

#### Progress Bar

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Track height           | 3--4px                                                   |
| Track color            | rgba(255,255,255,0.20) or #333333                        |
| Progress color         | #E50914 (Netflix red), #FF0000 (YouTube red), white      |
| Scrubber handle        | White circle, 12--16px diameter, appears on touch        |
| Elapsed time           | Left-aligned below bar, 12px, white                      |
| Remaining time         | Right-aligned below bar, 12px, muted gray, prefixed "-"  |
| Buffered indicator     | Slightly lighter than track (#555555)                    |

#### Playback Speed Controls

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Presentation           | Dropdown menu or bottom sheet                            |
| Options                | 0.5x, 0.75x, 1.0x (default/checked), 1.25x, 1.5x, 2.0x|
| Active indicator       | Checkmark next to selected speed                         |
| Background             | Semi-transparent dark panel                              |
| Text                   | White, 16px, regular weight                              |

#### Subtitle / Audio Selectors

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Presentation           | Overlay menu or dedicated screen                         |
| Menu items             | Share, Playback Speed, Languages, Subtitles              |
| Item layout            | Icon left + label right, full-width row                  |
| Separator              | Thin 1px divider line                                    |

### Content Detail

#### Hero Image Treatments

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Hero height            | 35--40% of viewport (bottom sheet style)                 |
| Hero width             | Full-bleed                                               |
| Close button           | Circular, top-right, dark bg with white X icon           |
| Mute/Volume toggle     | Small circular icon, bottom-right of hero                |
| Progress indicator     | Thin red bar (4px) at bottom edge of hero                |
| Background behavior    | Auto-play video preview with gradient overlay            |

#### Metadata Layout

| Element                | Style                                                    |
|------------------------|----------------------------------------------------------|
| Title                  | 20--28px, bold/heavy, white, left-aligned                |
| Year                   | 12--14px, regular, muted gray (#9A9A9A)                  |
| Duration               | 12--14px, regular, muted gray, "50 min" or "1h 32m"     |
| Rating badge           | Rounded rectangle, yellow fill (#FFD400), black text, 10--12px |
| Genre tags             | Small pills or inline text, separated by dots or pipes   |
| Match percentage       | Green text ("97% Match"), 14px, semibold (Netflix)       |
| Season/Episode         | "S1, E1" in bold, followed by episode title              |
| Content ratings        | Small icon badges: 4K, Dolby Vision, Dolby Atmos, CC, SDH, AD |

#### Action Buttons

| Button         | Style                                                          | Platform Reference     |
|----------------|----------------------------------------------------------------|------------------------|
| Play           | Large, white fill, black text + play icon, full-width rounded  | Netflix, HBO Max       |
| Resume         | Dark gray fill (#1C1C1E), blue text + play icon                | Apple TV               |
| Add to List    | Outlined or icon-only (+ icon), stacked vertically             | Netflix, HBO Max       |
| Download       | Icon-only (down arrow), stacked vertically                     | Netflix                |
| Share          | Icon-only (share icon), stacked vertically                     | Apple TV, Netflix      |
| Rate / Like    | Thumbs up/down icons, vertically stacked                       | Netflix                |

Button bar layout: Primary CTA is full-width. Secondary actions (Add, Download, Share, Rate) are arranged in a horizontal icon row below, each with a small label underneath.

#### Episode / Season Selectors

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Tab row                | "Episodes", "Collection", "More Like This", "Trailer"   |
| Active tab indicator   | Red underline bar (Netflix), white underline (others)    |
| Season selector        | Pill/dropdown, dark gray fill (#1A1A1A--#2B2B2B), white text, chevron icon |
| Season selector shape  | Rounded rectangle, ~32px height                          |
| Season selector position| Left-aligned, below tab row                             |

#### Episode List

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Card layout            | Horizontal: thumbnail left + text right                  |
| Thumbnail size         | ~120--140px wide, 16:9 aspect, rounded corners 6--8px    |
| Play icon overlay      | Translucent circle with play triangle, centered on thumb |
| Episode title          | Bold/heavy weight, 14--16px, white, some use ALL CAPS    |
| Duration               | Muted small text, 12px, gray                             |
| Description            | Multi-line paragraph, 13px, medium gray (#9E9E9E--#B3B3B3) |
| Download icon          | Per-episode download arrow, right-aligned                |
| Card spacing           | 16--20px vertical between episodes                       |

#### Synopsis / Description

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Font size              | 14--16px, regular weight                                 |
| Color                  | Light gray (#B3B3B3, #CFCFCF, or #E6E6E6)               |
| Line height            | ~22px (1.4 ratio)                                        |
| Max lines              | 3--4 lines with "MORE" expand link                       |
| Max width              | ~80% of screen width                                     |
| "MORE" link            | Same color or slightly brighter, semibold                |

#### "More Like This" Section

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Grid                   | 3-column poster grid (2:3 ratio)                         |
| Poster height          | ~160px                                                   |
| Corner radius          | 4--6px                                                   |
| Gap                    | 8px                                                      |
| Badges                 | "Top 10" red badge, "New" badge overlaid                 |

### Search & Browse

#### Search Bar Styles

| Property               | Dark Theme                        | Light Theme                      |
|------------------------|-----------------------------------|----------------------------------|
| Background             | Dark gray (#1C1C1E, #333333)      | Light gray (#F0F0F0, #F2F2F2)    |
| Text color             | White                              | Black                            |
| Placeholder color      | Medium gray (#8E8E93, #999999)    | Medium gray (#B0B0B0, #999999)   |
| Corner radius          | 8--12px (fully rounded pill)       | 8--12px                          |
| Height                 | 36--44px                           | 36--44px                         |
| Icons                  | Magnifying glass (left), microphone (right, Apple) | Same |
| Position               | Below page title, full width with 16px margin      | Same |

#### Category / Genre Chips

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Style                  | Rounded rectangle tiles in 2-column grid                 |
| Background             | Vibrant solid colors (unique per category)               |
| Text                   | Bold white, left-aligned with 12--16px padding           |
| Corner radius          | 8--12px                                                  |
| Height                 | ~80--100px                                               |
| Thumbnail              | Small tilted album/poster art, bottom-right corner       |
| Grid gap               | 8--12px                                                  |

Observed category colors (Spotify-style approach):
- Red-orange, Royal blue, Purple, Dark blue, Pink, Burnt orange, Green

#### Results Grid

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Layout                 | 2--3 column grid of poster thumbnails                    |
| Poster aspect ratio    | 2:3 or 16:9 depending on content type                   |
| Text below             | Title (14px bold) + metadata (12px gray)                 |
| Infinite scroll        | Vertical with loading indicator at bottom                |

### Subscription / Paywall

#### Plan Comparison Cards

| Property               | Value / Pattern                                          | Platform Reference     |
|------------------------|----------------------------------------------------------|------------------------|
| Layout                 | Vertically stacked rounded cards                         | HBO Max                |
| Card background        | Frosted-dark panel, rgba(20,20,20,0.55) with blur        | HBO Max                |
| Card border radius     | 14--20px                                                 | HBO Max                |
| Card shadow            | Soft outer shadow, 0px 6px 24px rgba(0,0,0,0.6)         | HBO Max                |
| Card margin            | 16px horizontal from screen edges                        | HBO Max                |
| Card spacing           | 20--24px vertical between cards                          | HBO Max                |
| Logo                   | Small, centered at top of card                           | HBO Max                |
| Plan name              | Large, heavy weight, centered                            | HBO Max                |
| Price                  | Large display weight number + smaller "/month" unit      | HBO Max, Luminary      |
| Feature bullets        | Left-aligned list, small regular-weight sans-serif, light gray | HBO Max          |
| Bullet markers         | Small white/light gray circles or squares                | HBO Max                |

#### CTA Buttons

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Style                  | Full-width rounded rectangle, high contrast              |
| Background             | White, brand color, or gradient                          |
| Text                   | Bold, centered, 16--18px                                 |
| Height                 | 48--56px                                                 |
| Corner radius          | 8--12px or fully rounded                                 |
| Position               | Sticky bottom or below cards                             |

#### Trial Messaging

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Style                  | Small text below CTA, muted gray                         |
| Content                | "Start your free trial", "Cancel anytime"                |
| Legal text             | Very small (10--11px), muted, bottom of screen           |

#### Apple TV+ Subscription Variant

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Background             | Vibrant gradient (purple to pink to orange)              |
| Cards                  | White rounded cards with subtle shadows                  |
| Pricing                | Strikethrough original price + bold discounted price     |
| CTA                    | Bright blue "Select" button per card                     |

### Profile & Settings

#### Profile Avatar Grids

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Avatar shape           | Circular or rounded square                               |
| Avatar size            | 32px (inline) -- 64--80px (profile page)                 |
| Border                 | Thin transparent or subtle gray border                   |
| Grid layout            | Horizontal row or 2x2 grid for profile selection         |

#### Settings List Styles

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Background             | Same as page background                                  |
| Row height             | 44--56px                                                 |
| Label                  | Left-aligned, 16px, white/primary                        |
| Detail text            | Right-aligned, 14px, muted gray                          |
| Chevron                | Right-aligned gray chevron icon                          |
| Divider                | Thin 1px line, #1F1F1F--#2E2E2E                          |
| Section headers        | Uppercase, small (12px), muted, extra letter-spacing     |

#### Account Management

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Bottom sheet style     | Rounded top corners (12--18px), dark charcoal bg         |
| Menu items             | Vertically stacked with icon + label + chevron           |
| Version text           | Bottom of sheet, very small muted gray                   |
| Sign out               | Red text or separate section at bottom                   |

### Lists & Downloads

#### Watchlist Item Layouts

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Card layout            | Horizontal: poster thumbnail left + text right           |
| Thumbnail              | ~80--100px wide, 2:3 ratio, rounded corners 4--6px       |
| Title                  | 16px bold, white, 1--2 lines                             |
| Metadata               | 12--13px, muted gray (year, rating, duration)            |
| Action icon            | Download arrow or checkmark, right side                  |

#### Download Progress

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Indicator style        | Circular progress ring or horizontal bar                 |
| Progress color         | Accent color (blue or brand color)                       |
| Background track       | Muted gray (#333333)                                     |
| Percentage text        | Small, centered in circular indicator                    |

#### Continue Watching Progress Bars

| Property               | Value / Pattern                                          |
|------------------------|----------------------------------------------------------|
| Position               | Bottom edge of poster thumbnail                          |
| Height                 | 3--4px                                                   |
| Track color            | Dark gray (#333333)                                      |
| Progress color         | Red (#E50914 Netflix) or white                           |
| Corner radius          | 2px                                                      |

---

## 5. Motion & Animation

### Transition Patterns

| Transition                        | Duration     | Easing                               | Usage                    |
|-----------------------------------|-------------|--------------------------------------|--------------------------|
| Bottom sheet slide-up             | 240--350ms  | Ease-out cubic-bezier(0.0, 0.0, 0.2, 1.0) | Content detail modal |
| Backdrop fade                     | 150--300ms  | Linear to ease-out                   | Scrim behind sheets      |
| Modal fade + slide                | 300ms       | Ease-out cubic-bezier                | Overlay entry            |
| Tab content switch                | 160ms       | Ease-out                             | Tab change animation     |
| Card press state                  | 120ms       | Ease-out                             | Scale down to 0.98       |
| Micro-interaction (chip select)   | 160--180ms  | Ease-out                             | Background color change  |
| Carousel item snap                | 200--300ms  | Decelerate                           | Horizontal scroll snap   |
| Fade-out dismiss                  | 150--200ms  | Ease-in                              | Modal/sheet dismiss      |

### Loading States

| Pattern                           | Description                                              |
|-----------------------------------|----------------------------------------------------------|
| Skeleton screens                  | Gray placeholder rectangles matching content layout       |
| Skeleton colors                   | #2A2A2A (dark theme) / #E0E0E0 (light theme)            |
| Skeleton animation                | Shimmer gradient sweep left-to-right, ~1.5s cycle        |
| Thumbnail placeholder             | Gray rectangle with rounded corners matching actual cards |
| Text placeholder                  | Narrow gray bars (2--3 lines) below thumbnail skeleton   |

### Micro-interactions

| Interaction                       | Animation                                                |
|-----------------------------------|----------------------------------------------------------|
| Like/Add button press             | Scale 0.9 then 1.0 with subtle bounce, ~200ms           |
| Badge appear (selected chip)      | Fill white + pulse scale 1.0 -> 1.05 -> 1.0, ~180ms     |
| Play overlay fade-in              | Opacity 0 -> 1, ~180--220ms ease                         |
| Progress bar scrub                | Thumb scale-up on touch, ~100ms                          |
| Hero video transition             | Dissolve / crossfade between hero items                  |

---

## 6. Iconography

### Icon Styles

| Style                  | Usage                                                    | Platform Reference     |
|------------------------|----------------------------------------------------------|------------------------|
| Outline (stroke)       | Default/inactive state for nav, action icons             | All platforms          |
| Filled (solid)         | Active/selected state for nav icons                      | Netflix, Apple TV      |
| Line icons             | Thin, consistent 1.5--2px stroke weight                  | Netflix, HBO Max       |
| System SF Symbols      | iOS-native icon set                                      | Apple TV               |
| Material Icons         | Android-native icon set                                  | YouTube (Android)      |

### Common Streaming Icons

| Icon                   | Usage                                  | Typical Size  |
|------------------------|----------------------------------------|---------------|
| Play triangle          | Play button, resume, trailer CTAs      | 20--56px      |
| Download arrow         | Download for offline, per-episode DL   | 20--24px      |
| Plus (+)               | Add to watchlist/my list               | 20--24px      |
| Checkmark              | Item in list, completed, selected      | 20--24px      |
| Share (box + arrow)    | Share content                          | 20--24px      |
| Bell                   | Notifications                          | 24--28px      |
| Magnifying glass       | Search                                 | 20--24px      |
| Cast/AirPlay           | Cast to TV, highlighted blue when active| 24--28px     |
| Close (X)              | Dismiss, close player/modal            | 24--28px      |
| Chevron (>)            | Navigate forward, expand               | 16--20px      |
| Back (<)               | Navigate back                          | 20--24px      |
| Speaker / Mute         | Audio controls                         | 20--24px      |
| Thumbs up/down         | Rate content                           | 20--24px      |
| Info (i)               | Content details, about                 | 20--24px      |
| PiP (nested rectangles)| Picture-in-picture toggle              | 24px          |
| Ellipsis (...)         | More options menu                      | 20--24px      |
| Gear                   | Settings, quality options               | 24px          |
| Rewind/Forward 10s     | Skip back/forward with "10" label      | 36--44px      |

### Icon Sizes

| Context                | Size        | Padding/Tap Target |
|------------------------|-------------|-------------------|
| Tab bar icons          | 24--28px    | 44px minimum      |
| Player controls        | 36--56px    | 56--64px           |
| Action bar icons       | 20--24px    | 44px minimum      |
| Inline metadata icons  | 12--16px    | --                 |
| Navigation back/close  | 24--28px    | 44px minimum      |

---

## 7. Key UX Patterns by App

### Netflix

- **Visual identity**: Pure black (#000000) backgrounds, signature red (#E50914) as sole accent color
- **Bottom sheet pattern**: Content detail appears as a slide-up bottom sheet with hero video auto-playing at top
- **Episode list**: Uppercase/bold episode titles, horizontal layout (thumb left, text right), red progress indicator
- **Tab structure**: Episodes | Collection | More Like This | Trailer (with red underline active indicator)
- **Profile**: "My Netflix" section with profile avatar, downloads card, liked content
- **Maturity badges**: Yellow rounded rectangle (#FFD400) with black text (e.g., "12+")
- **"Top 10" badges**: Red badge overlaid on poster thumbnails
- **Download & Go**: Feature discovery modal with centered visual hierarchy and red accent
- **Typography**: Bold, rounded grotesque headings (Netflix Sans), very heavy weights for display

### HBO Max

- **Visual identity**: Near-black with cinematic gradients, purple/maroon warm tones for dramatic effect
- **Onboarding**: Genre/brand preference selection with pill chips (white selected, dark gray unselected)
- **Step indicators**: Circular steps connected by thin lines, active = white filled, inactive = gray outlined
- **Hero carousel**: Full-bleed hero poster occupying up to 70% viewport, with show title in large stylized serif/display type with metallic/gold treatment
- **Subscription**: Frosted-glass dark cards with plan tiers, large centered plan name, price, and feature bullets
- **Category navigation**: Horizontal top row with "Home", "Series", "Movies" as text labels
- **Content rating selector**: Vertical stepper/timeline with blue nodes and stacked rounded cards
- **Card style**: Semi-opaque frosted-dark panels with soft outer shadows and large corner radii

### Disney+

- **Visual identity**: Deep blue/dark backgrounds, signature Disney blue accent
- **Profile selection**: Character avatars in grid, playful design language
- **Collections**: Brand-focused carousels (Disney, Pixar, Marvel, Star Wars, National Geographic)
- **Content grouping**: Strong franchise/universe grouping
- **Download behavior**: Per-episode download with progress rings

### YouTube

- **Light mode default**: White (#FFFFFF) backgrounds with black text, red (#FF0000) brand accent
- **Home feed**: Vertical scroll of video cards (16:9 thumbnail + metadata below)
- **Shorts section**: Horizontally scrollable rounded thumbnails with text overlays
- **Bottom nav**: 5 tabs including central (+) create button
- **Category filter**: Horizontal pill tabs (All, Music, Gaming, etc.) below header
- **Channel page**: Circular avatar, channel name with verification badge, subscribe button
- **Mini player**: Persistent bottom bar with thumbnail and playback controls
- **Skeleton loading**: Gray rectangles matching thumbnail and text layout

### Apple TV+

- **Dual theme**: Supports both light (white bg, black text) and dark (black bg, white text) modes
- **Search**: 2-column category grid with colorful tiles, each with overlay text and images
- **Bottom nav**: 5 tabs with blue (#007AFF) active state, gray inactive
- **Hero cards**: Large promotional cards with rounded corners and call-to-action text
- **Content detail**: Hero image + "Resume Episode" button with dark gray fill and blue text
- **Metadata badges**: TV-PG, 4K, Dolby Vision, Dolby Atmos, CC, SDH, AD as small icon badges
- **How to Watch**: Separate section with service logo and subscription status
- **Up Next**: Playlist-style queue with thumbnails and episode info
- **Player**: Clean controls with rewind/forward 10s, PiP, AirPlay (blue #007AFF), playback speed dropdown

### Others

- **Crunchyroll**: Dark theme with orange accent, anime-focused UI, episode lists with preview thumbnails
- **MasterClass**: Dark mode, red accent underline on active tabs, instructor thumbnails with duration overlays
- **Showcase**: Dark browsing interface, soft yellow (#E3D68B) for section titles, network-based organization
- **Luminary**: Dark navy (#0A1F44) backgrounds, cyan (#00AAFF) accent, podcast-focused card layouts

---

## 8. Mobile vs Tablet Considerations

### Layout Adaptations

| Aspect                  | Mobile (Phone)                        | Tablet                               |
|-------------------------|---------------------------------------|---------------------------------------|
| Navigation              | Bottom tab bar (5 tabs)               | Side rail or top bar + bottom tabs    |
| Grid columns (posters)  | 3 columns                             | 5--7 columns                          |
| Grid columns (landscape)| 1--2 columns                          | 3--4 columns                          |
| Hero height             | 35--40% viewport                      | 30--35% viewport                      |
| Horizontal carousels    | 2--3 items visible                    | 4--6 items visible                    |
| Content padding          | 16px                                  | 24--32px                              |
| Search grid             | 2 columns                             | 3--4 columns                          |
| Player controls         | Compact, overlaid                     | More spacious, possible side panel    |
| Episode list            | Single column, stacked                | Two-column or wider cards             |

### Content Density

| Aspect                  | Mobile                                | Tablet                               |
|-------------------------|---------------------------------------|---------------------------------------|
| Cards per row           | 2--3                                  | 4--6                                  |
| Text truncation         | More aggressive (1--2 lines)          | Relaxed (2--3 lines)                 |
| Section spacing         | 24--32px                              | 32--48px                              |
| Font sizes              | Standard scale                        | Slightly larger body (16--18px)       |

### Navigation Changes

- **Phone**: Bottom tab bar is universal, always visible except during video playback
- **Tablet**: Some platforms use a left side rail or split-view navigation
- **Player on tablet**: May show episode list alongside player (split-view)

---

## 9. Accessibility

### Contrast Ratios

| Text Type                              | Foreground  | Background  | Estimated Ratio | WCAG Level |
|----------------------------------------|-------------|-------------|----------------|------------|
| Primary text on dark bg                | #FFFFFF     | #000000     | 21:1           | AAA        |
| Secondary text on dark bg              | #B3B3B3     | #000000     | 10.5:1         | AAA        |
| Tertiary/muted text on dark bg         | #8E8E93     | #000000     | 5.5:1          | AA         |
| Inactive text on dark bg               | #666666     | #000000     | 3.7:1          | AA (large) |
| Red accent on dark bg                  | #E50914     | #000000     | 5.2:1          | AA         |
| Primary text on light bg               | #000000     | #FFFFFF     | 21:1           | AAA        |
| Secondary text on light bg             | #606060     | #FFFFFF     | 5.7:1          | AA         |
| Placeholder on light bg                | #B0B0B0     | #FFFFFF     | 2.6:1          | Fails      |

### Touch Target Sizes

| Element                                | Minimum Size                                            |
|----------------------------------------|---------------------------------------------------------|
| Tab bar icons                          | 44x44px (Apple HIG) / 48x48dp (Material)               |
| Player controls (play/pause)           | 56--64px                                                |
| Player controls (rewind/forward)       | 44px minimum                                            |
| Navigation back/close                  | 44px minimum                                            |
| Action icons (like, share, download)   | 44px minimum                                            |
| Chips / pills                          | 32--36px height, full touch area                        |
| List rows                              | 44--56px height minimum                                 |
| Toggle switches                        | 51x31px (iOS standard)                                  |

### Dynamic Type Support

- All major streaming platforms use system fonts or proportional scaling
- Text sizes should scale with system accessibility settings
- Layout should accommodate 200% text size without breaking
- Fixed-size badges and icons remain constant; only text content scales

---

## 10. Key Takeaways & Recommendations

### Common Patterns Across All Platforms

1. **Dark theme dominance**: Every major streaming platform uses dark backgrounds as default. Black (#000000) or near-black (#0A0A0A--#121212) is universal.
2. **High contrast text**: White (#FFFFFF) on black with clear secondary (#B3B3B3) and tertiary (#8E8E93) text levels.
3. **Content-first design**: UI chrome is minimal and recedes. Content imagery is the primary visual element.
4. **Bottom tab navigation**: 5 tabs is the standard, with filled/colored active state and outline/gray inactive state.
5. **Horizontal carousels**: The primary content browsing pattern. Every platform uses rows of scrollable content cards with section headers.
6. **Hero/featured content**: Full-bleed hero image or video at top of home screen, occupying 35--70% of viewport.
7. **Bottom sheet pattern**: Content detail frequently presented as a slide-up bottom sheet rather than a full page navigation.
8. **Red accent for progress**: Video progress bars consistently use red (#E50914 or #FF0000) across Netflix and YouTube.
9. **Generous corner radii**: Cards use 4--8px, modals/sheets use 12--18px, buttons use 8--12px or fully rounded.
10. **System fonts**: Most platforms use platform-native system fonts for body text, reserving brand fonts for display/hero text only.

### Differentiators

| Aspect                | Netflix              | HBO Max              | Apple TV+            | YouTube              |
|-----------------------|----------------------|----------------------|----------------------|----------------------|
| Default theme         | Dark only            | Dark only            | System (light/dark)  | Light default        |
| Accent color          | Red (#E50914)        | White / Purple       | Blue (#007AFF)       | Red (#FF0000)        |
| Hero treatment        | Video auto-play      | Poster with gradient | Card with CTA text   | Thumbnail feed       |
| Detail pattern        | Bottom sheet         | Full page            | Full page            | Scroll down          |
| Typography weight     | Very heavy (800+)    | Heavy condensed      | Medium/system        | Regular/medium       |

### Recommended Patterns for CloudStream

1. **Primary theme**: Dark theme with pure black (#000000) background. Support light theme as secondary option.
2. **Accent color**: Define a brand accent (consider a distinctive non-red color to differentiate from Netflix/YouTube -- perhaps teal, indigo, or green).
3. **Typography**: Use system fonts (Roboto/SF Pro) with the type scale defined above. Reserve heavy weights (700--800) for display/hero text.
4. **Spacing**: Adopt the 4/8/12/16/24/32px spacing scale. Use 16px as the base horizontal padding.
5. **Navigation**: 5-tab bottom bar with Home, Search/Browse, Library/Downloads, Sources/Extensions, Settings.
6. **Home screen**: Hero carousel at top (35--40% viewport) + horizontal content rows with section headers.
7. **Content detail**: Bottom sheet pattern (Netflix-style) for quick previews; full page for complete detail view.
8. **Video player**: Follow Apple TV+ player layout with close, PiP, cast, volume at top; rewind/play/forward centered; progress bar with timestamps below.
9. **Episode list**: Horizontal card layout (thumbnail left, text right) with season dropdown selector.
10. **Search**: Full-width search bar + 2-column category tile grid with colorful backgrounds.
11. **Corner radii**: Use consistent scale -- 4px (thumbnails), 8px (cards, inputs), 12px (modals, sheets), full-round (pills, chips).
12. **Progress indicators**: Thin red/accent bar at bottom of thumbnails for "continue watching" state.
13. **Loading**: Skeleton screens matching content layout with shimmer animation.
14. **Motion**: Keep animations in the 150--350ms range with ease-out easing. Bottom sheets at 240--320ms, micro-interactions at 120--180ms.
15. **Accessibility**: Maintain minimum 4.5:1 contrast for body text, 3:1 for large text. All touch targets minimum 44x44px.
