# CloudStream — Project Conventions

## Architecture

- **Clean Architecture**: domain → data → presentation layers, strictly separated
- **Multimodule**: `:core:*` for shared logic, `:feature:*` for screens, `:designsystem` for UI components, `:library` for providers/extractors
- **SOLID principles**: Single responsibility per class, depend on abstractions, open for extension
- **Unidirectional data flow**: ViewModels expose `StateFlow<UiState>`, screens are stateless composables that emit events via lambdas

## Code Rules

### No Hardcoded Values
- Colors: `CSTheme.colors.*` — NEVER use `Color(0xFF...)` or `Color.White` directly
- Typography: `CSTheme.typography.*` — NEVER use `TextStyle(fontSize = ...)` directly
- Spacing: `CSTheme.spacing.*` — NEVER use raw `dp` values for padding/margins (use `CSTheme.spacing.lg` not `16.dp`)
- Shapes: `CSTheme.shapes.*` or `MaterialTheme.shapes` or `LocalDGShapes` — NEVER use `RoundedCornerShape(8.dp)` directly
- Icons: `CSIcons.*` — NEVER use `Icons.Default.*` or `Icons.Filled.*` directly
- Alpha/Opacity: `AlphaDefaults.*` — NEVER use raw float opacity values
- Sizes: `CSTheme.sizes.*` — NEVER hardcode icon sizes, button heights, etc.
- Strings: Use string resources — NEVER hardcode user-visible text

### Design System First
- Before creating ANY new component, search `designsystem/src/commonMain/` for existing ones
- Before creating ANY new constant, search `tokens/` for existing tokens
- Reuse `CSPosterCard`, `CSLandscapeCard`, `CSHeroCard`, `CSContentRow`, `CSSearchBar`, `CSChip`, `CSBadge`, etc.
- Reuse screen layouts: `HomeScreen`, `MovieDetailScreen`, `TvShowDetailScreen`

### Code Lookup Protocol
Before writing new code, ALWAYS run these searches:
```
Grep pattern="ClassName" path="designsystem/" — check design system
Grep pattern="ClassName" path="library/src/commonMain/" — check library
Grep pattern="ClassName" path="core/" — check core modules
Glob pattern="**/*FileName*" — find existing files
```

### Layer Separation
- **Domain models** are plain Kotlin data classes — no framework dependencies
- **API/network models** use `@Serializable` — live in data layer
- **UI state models** live alongside their screens — contain only display data
- **Mappers** convert between layers: `ApiModel.toDomain()`, `DomainModel.toUiState()`
- **Repository interfaces** in `:core:domain`, implementations in `:core:data`
- **Use cases** are single-purpose classes with `operator fun invoke()`

### ViewModel Pattern
```kotlin
class FeatureViewModel(
    private val useCase: SomeUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeatureUiState())
    val uiState: StateFlow<FeatureUiState> = _uiState.asStateFlow()

    fun onEvent(event: FeatureEvent) { ... }
}
```

### Testing
- Every ViewModel must have unit tests
- Every UseCase must have unit tests
- Every Mapper must have unit tests
- Use fakes over mocks where possible
- Screenshot tests via Roborazzi for screen layouts

## Responsive Design
- Use `responsiveValue(compact, medium, expanded)` for size-dependent values
- Use `CSTheme.isTV` for TV-specific behavior
- Constrain content width on tablet/TV (max 640-720dp for detail content)
- Test all screens at phone (393dp), tablet (800dp), and TV (1280dp) widths

## File Organization
```
feature/featurename/
├── data/
│   ├── repository/FeatureRepositoryImpl.kt
│   ├── mapper/FeatureMapper.kt
│   └── source/FeatureDataSource.kt
├── domain/
│   ├── model/FeatureModel.kt
│   ├── repository/FeatureRepository.kt
│   └── usecase/GetFeatureUseCase.kt
└── presentation/
    ├── FeatureScreen.kt
    ├── FeatureViewModel.kt
    └── FeatureUiState.kt
```

## Git Conventions
- Commit messages: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`
- One feature per branch when possible
- Run screenshot tests before committing UI changes

## Key Dependencies
- **HTTP**: Ktor 3.1.2 (KMP) via `CloudStreamClient` in library/
- **JSON**: kotlinx.serialization 1.8.1
- **Images**: Coil 3.3.0 (KMP)
- **Async**: kotlinx.coroutines 1.10.2
- **HTML**: Ksoup 0.2.6 (KMP)
- **UI**: Compose Multiplatform 1.8.0
- **Player**: Media3/ExoPlayer 1.9.2 (Android), AVPlayer (iOS)

## Reference Documents
- Design inspiration: `inspirations/DESIGN_SYSTEM.md` (836 lines)
- Inspiration index: `inspirations/INDEX.md`
- Implementation prompt: `INITIAL_PROMPT.md`
- Existing Android app: `app/src/main/java/com/lagradost/cloudstream3/`
