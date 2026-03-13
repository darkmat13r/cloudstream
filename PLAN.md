# iOS Support Migration Plan

Incremental migration to make CloudStream's library module compile for iOS, then build a full iOS app. Each phase produces a buildable project.

---

## Phase 1: Jackson → kotlinx.serialization
**Files: ~75 | Annotations: ~1300**

Replace Jackson with kotlinx.serialization across the entire codebase.

### Changes:
1. **Build config**: Add `kotlinx.serialization` plugin + dependency to `libs.versions.toml` and both `build.gradle.kts` files. Remove Jackson dependency.
2. **AppUtils.kt** (library): Replace `mapper.writeValueAsString()` / `mapper.readValue()` with `Json.encodeToString()` / `Json.decodeFromString()`
3. **MainAPI.kt** (library): Remove `mapper` global, create a `val json = Json { ignoreUnknownKeys = true }` instance
4. **MainActivity.kt** (library): Update NiceHttp `ResponseParser` to use kotlinx.serialization temporarily (NiceHttp is replaced in Phase 2)
5. **All data classes** (~68 files):
   - Add `@Serializable` annotation to every serialized data class
   - Replace `@JsonProperty("name")` → `@SerialName("name")`
   - Replace `@JsonIgnore` → `@Transient`
   - Replace `@JsonAutoDetect` → remove (not needed with kotlinx.serialization)
6. **App module** (~26 files): Same annotation/API replacements (DataStore.kt, BackupUtils.kt, sync providers, etc.)

### Risk:
- kotlinx.serialization requires compile-time plugin; classes without `@Serializable` will fail at compile time (which is actually safer than Jackson's runtime reflection)
- Polymorphic serialization (if any) needs explicit `SerializersModule` config

---

## Phase 2: NiceHttp + OkHttp → Ktor Client
**Files: ~50+ | HTTP calls: 254+**

Replace the HTTP layer with Ktor, which is fully KMP-compatible.

### Changes:
1. **Build config**: Add Ktor client dependencies (core, content-negotiation, serialization, platform engines). Remove NiceHttp + OkHttp deps from library module.
2. **Create `CloudStreamClient.kt`** (library/commonMain): A thin wrapper around Ktor that preserves the familiar `app.get()` / `app.post()` API pattern so extractors need minimal changes. Returns a response object with `.text`, `.url`, `.document` (via Ksoup), `.body<T>()` (via kotlinx.serialization).
3. **MainActivity.kt**: Replace `var app = Requests(...)` with new `CloudStreamClient` instance
4. **All extractors** (~50 files): Update `app.get/post` calls to new client API. If we match the API shape, most changes are just import swaps.
5. **App module network layer**: `RequestsHelper.kt`, `CloudflareKiller.kt`, `DdosGuardKiller.kt` need platform-specific implementations via expect/actual (OkHttp interceptors → Ktor plugins)

### Platform engines:
- Android/JVM: `ktor-client-okhttp` (keeps OkHttp under the hood on Android)
- iOS: `ktor-client-darwin` (uses URLSession)

---

## Phase 3: JSoup → Ksoup
**Files: 9 direct + 45 via `.document`**

Replace JSoup with [Ksoup](https://github.com/nicehash/ksoup) (fleeksoft), which provides a JSoup-compatible API for KMP.

### Changes:
1. **Build config**: Add `com.fleeksoft.ksoup:ksoup` KMP dependency. Remove JSoup.
2. **Import replacements**: `org.jsoup.Jsoup` → `com.fleeksoft.ksoup.Ksoup`, `org.jsoup.nodes.*` → `com.fleeksoft.ksoup.nodes.*`, etc.
3. **`Jsoup.parse(html)`** → **`Ksoup.parse(html)`** in 9 files
4. **`.document` extension**: Already handled in Phase 2 — the new `CloudStreamClient` response will use Ksoup to parse HTML.
5. **CSS selectors, `.attr()`, `.text()`, `.html()`, `.data()`**: API-compatible, should work as-is.

### Risk:
- Ksoup may have minor API differences in edge cases. Need to test extractors.

---

## Phase 4: java.* → Kotlin/KMP alternatives
**Files: 31 in commonMain**

Replace JVM-specific standard library usage with cross-platform alternatives.

### Changes:
| JVM API | Replacement | Files |
|---------|-------------|-------|
| `java.net.URI` | `io.ktor.http.Url` or simple `expect/actual` | ~8 files |
| `java.net.URLEncoder/Decoder` | Ktor `encodeURLParameter()` / custom | 3 files |
| `javax.crypto.*` (Cipher, SecretKeySpec, IvParameterSpec, GCMParameterSpec) | `expect/actual` → Android: javax.crypto, iOS: CommonCrypto/CryptoKit | 7 files |
| `java.text.SimpleDateFormat` | `expect/actual` or kotlinx-datetime | 3 files |
| `java.util.regex.Pattern/Matcher` | Kotlin `Regex` | 2 files |
| `java.util.Collections.synchronizedList` | `kotlinx.coroutines` mutex or expect/actual | 1 file |
| `java.util.Locale` | `expect/actual` | 2 files |
| `java.util.UUID` | `kotlin.uuid.Uuid` (Kotlin 2.x) or expect/actual | 2 files |
| `java.io.Reader` | Remove (AppUtils) | 1 file |
| `java.io.IOException` | `kotlin.io` or custom | 1 file |
| `java.lang.ref.WeakReference` | `expect/actual` | 1 file |
| `java.nio.charset.StandardCharsets` | Kotlin `Charsets` | 2 files |
| `java.nio.ByteBuffer` | Kotlin `ByteArray` operations | 1 file |

---

## Phase 5: Remaining JVM-only libraries
**Files: ~11**

### Changes:
| Library | Replacement | Files |
|---------|-------------|-------|
| **Rhino** (JS engine) | `expect/actual`: Android → Rhino, iOS → JavaScriptCore (via Kotlin/Native C-interop) | 2 files |
| **FuzzyWuzzy** | Simple Kotlin implementation of fuzzy string matching (Levenshtein + ratio), or existing KMP lib | 5 files |
| **NewPipeExtractor** | `expect/actual`: Android/JVM → NewPipeExtractor, iOS → stub/disabled initially | 3 files |
| **tmdb-java** | Replace with Ktor-based TMDB API calls (simple REST client) | 1 file |

---

## Phase 6: Add iOS targets to library module
**No code changes to existing files — purely additive**

### Changes:
1. **library/build.gradle.kts**: Add iOS targets:
   ```kotlin
   iosX64()
   iosArm64()
   iosSimulatorArm64()
   ```
2. **Create `library/src/iosMain/`** source set with `actual` implementations:
   - `Log.kt` → `NSLog` wrapper
   - `ContextHelper.kt` → No-op or simple object store
   - `Coroutines.kt` → `Dispatchers.Main`
   - `WebViewResolver.kt` → WKWebView or stub
   - Crypto actuals → CommonCrypto
   - URI/Date actuals → Foundation (NSURL, NSDateFormatter)
   - Rhino actual → JavaScriptCore (JSContext)
3. **Configure iOS framework export** for consumption by the iOS app

---

## Phase 7: iOS App (Compose Multiplatform)

### Changes:
1. **Create `:iosApp` module** with Compose Multiplatform
2. **Shared UI module** (`:shared`) for screens that can be shared between Android and iOS using Compose Multiplatform
3. **Core screens**: Home, Search, Result/Detail, Player, Settings
4. **Media playback**: AVPlayer on iOS (via expect/actual or Swift interop)
5. **Navigation**: Compose Navigation (multiplatform)
6. **Storage**: NSUserDefaults via expect/actual for DataStore equivalent

---

## Execution Order & Dependencies

```
Phase 1 (Jackson → kotlinx.serialization)
    ↓
Phase 2 (NiceHttp → Ktor)
    ↓
Phase 3 (JSoup → Ksoup)
    ↓
Phase 4 (java.* cleanup)
    ↓
Phase 5 (Rhino, FuzzyWuzzy, NewPipe, TMDB)
    ↓
Phase 6 (Add iOS target)
    ↓
Phase 7 (iOS App)
```

Each phase is independently buildable and testable on Android. No phase breaks the Android build.
