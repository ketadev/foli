# Design: FOLI-00 MVP Project Foundation

## Technical Approach

Keep the existing `shared` Compose app shell and native entry points, while adding five narrowly scoped Kotlin Multiplatform `core` modules. Wire only infrastructure needed to prove the Android and iOS foundation: source-set dependencies, compiled design values, a small platform service bound through Koin, and app-entry startup. Do not add feature behavior, persisted schema, remote calls, or book-cover UI. This implements the three FOLI-00 delta specifications without treating the larger architecture target in `docs/architecture.md` as code already present.

The current Gradle pattern is Kotlin `2.4.20`, AGP `9.1.1`, the `com.android.kotlin.multiplatform.library` plugin, `android { ... }` inside `kotlin`, and explicit `iosArm64()` / `iosSimulatorArm64()` targets. New modules should follow that pattern rather than introduce `androidTarget()` or Android-library conventions from an older KMP template. New library versions and their Kotlin/AGP/iOS compatibility must be checked against primary vendor documentation and resolved on both targets during implementation; this design does not guess version pins.

## Architecture Decisions

### Decision: One responsibility per core module

**Choice**: `core:model` is common-only model/rule space with Android/iOS targets; `core:database` owns Room/SQLite/KSP setup without schema; `core:data` owns DataStore, Ktor, serialization, and later repository implementations; `core:designsystem` owns Compose token values and theme-facing APIs; `core:platform` owns a minimal platform abstraction and native adapters. `shared` assembles the app and Koin graph.

**Alternatives considered**: A generic `core:common` module, feature placeholders, or placing all dependencies in `shared`.

**Rationale**: These boundaries match `docs/architecture.md` and the specification while preventing empty feature scaffolding from masquerading as implemented behavior. Dependencies point inward: `core:data` may depend on `core:model` and `core:database`; `core:database` may depend on `core:model` if a real mapping requires it later; `core:designsystem` and `core:platform` remain independent of data; `shared` consumes the core modules. Do not add an unused edge merely because it might be needed later.

### Decision: Follow the current KMP source-set layout

**Choice**: Give each core module the existing `shared/build.gradle.kts` Android and iOS target pattern, with module-specific namespaces and dependencies. Put genuinely multiplatform libraries in `commonMain`, Android APIs and the Android Ktor engine in `androidMain`, and Darwin engine / iOS APIs in `iosMain`. Configure Room's KSP processor for both Android and iOS compilations in `core:database`, but create no entities, database class, generated schema, or migrations in this story.

**Alternatives considered**: JVM-only library modules or common-source-set declarations of platform engines.

**Rationale**: Both app targets must resolve the modules, and the current AGP/KMP project already expresses its Android target through the Android Multiplatform Library plugin. Room compiler configuration without an invented schema proves readiness without violating the no-feature-data scope.

### Decision: Native-owned startup with a guarded shared initializer

**Choice**: Start Koin from an Android `Application.onCreate` and from the Swift `@main App` initialization path, before creating `App()` / `MainViewController()`. Each calls a platform-specific entry in `shared` that contributes common and native modules to a shared, idempotent initializer. The initializer guards the process-wide start and does not restart Koin when the Compose shell is recreated; it should not silently accept an unrelated graph already started by another owner. Use constructor injection in foundation services, not global service lookup in domain code.

**Alternatives considered**: Start Koin inside `@Composable App`, start in Android `MainActivity`, or start inside the iOS `UIViewControllerRepresentable` creation callback.

**Rationale**: Compose previews, Activity recreation, and SwiftUI view recreation can re-enter those locations. The native application lifecycle provides one process-level owner; the shared guard enforces the specification's repeat-entry behavior. The exact Koin startup and Swift-export API names need confirmation against the selected official Koin/KMP release during implementation.

### Decision: Compile design tokens into Compose types

**Choice**: Translate the existing `design/tokens/design-tokens.json` into source-controlled Kotlin color, spacing, radius, size, and typography values in `core:designsystem`, with day/night palettes. The JSON remains the design source, not a packaged runtime resource. Apply a theme adapter to `shared` without replacing its starter screen content. Use available typefaces or explicit fallbacks until distributable font assets are supplied; do not claim Young Serif/Nunito are bundled merely because their names occur in JSON.

**Alternatives considered**: Runtime JSON parsing or an unverified code-generation plugin.

**Rationale**: Typed values compile on both platforms and remain available when the JSON is absent from app packages. Hand-mapping is proportionate for this fixed token set and can be checked against the source file.

### Decision: Assemble and test without production side effects

**Choice**: Separate module-list construction from process-wide Koin startup so Android and iOS tests can create isolated test graphs. Bind a small, real `core:platform` service in each platform module and resolve it in smoke tests; avoid opening a database, preferences file, or HTTP connection merely to test graph assembly. Catalogue Coil 3 for later use, but do not add it as an active `shared` dependency or render a cover.

**Alternatives considered**: Tests that start/stop the global graph or fabricated feature repositories/entities.

**Rationale**: Isolated graphs avoid cross-test startup conflicts, and a real platform binding makes assembly observable without importing future-story behavior.

## Data Flow

```text
Android Application ──→ shared Android initializer ──┐
                                                      ├─→ common + platform Koin modules
Swift @main App ──────→ shared iOS initializer ───────┘            │
                                                                    ↓
native UI entry ──────→ shared App() ──→ compiled design values + platform service

design/tokens/design-tokens.json ──(implementation-time mapping)──→ core:designsystem Kotlin
```

No user data, HTTP request, image download, or database write occurs in this story. `core:data` and `core:database` supply compilation-ready dependencies, not feature services.

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `settings.gradle.kts` | Modify | Include exactly the five new `:core:*` projects. |
| `build.gradle.kts` | Modify | Declare new plugin aliases with `apply false` where required for subprojects. |
| `gradle/libs.versions.toml` | Modify | Pin verified Koin, Ktor, serialization, Coroutines, Navigation, Room, SQLite, KSP, DataStore, and Coil 3 aliases; retain existing Compose/Android setup. |
| `core/model/build.gradle.kts`, `core/data/build.gradle.kts`, `core/database/build.gradle.kts`, `core/designsystem/build.gradle.kts`, `core/platform/build.gradle.kts` | Create | KMP Android/iOS module configuration and narrowly placed dependencies. |
| `core/platform/src/commonMain/kotlin/com/ketadev/foli/core/platform/PlatformInfo.kt` and platform counterparts | Create | Minimal platform-facing interface and Android/iOS implementations for real graph smoke coverage. |
| `core/designsystem/src/commonMain/kotlin/com/ketadev/foli/core/designsystem/FoliTokens.kt` | Create | Compile-time day/night palette and dimension/type values mapped from token JSON. Split by token family if readability requires it. |
| `shared/build.gradle.kts` | Modify | Consume foundation modules and common ViewModel/Koin/Navigation APIs. |
| `shared/src/commonMain/kotlin/com/ketadev/foli/di/AppKoin.kt` | Create | Shared module list and guarded startup contract. |
| `shared/src/androidMain/kotlin/com/ketadev/foli/di/AppKoin.android.kt`, `shared/src/iosMain/kotlin/com/ketadev/foli/di/AppKoin.ios.kt` | Create | Platform graph contributions and native-callable initialization. |
| `shared/src/commonMain/kotlin/com/ketadev/foli/App.kt` | Modify | Consume the compiled theme/tokens while preserving starter screen behavior; introduce only a root typed navigation route if needed to prove route compilation. |
| `androidApp/src/main/kotlin/com/ketadev/foli/FoliApplication.kt`, `androidApp/src/main/AndroidManifest.xml` | Create / Modify | Start shared Koin once in the Android application lifecycle. |
| `iosApp/iosApp/iOSApp.swift` | Modify | Start shared Koin before the SwiftUI window creates the Compose controller. |
| `shared/src/androidHostTest/kotlin/com/ketadev/foli/SharedLogicAndroidHostTest.kt`, `shared/src/iosTest/kotlin/com/ketadev/foli/SharedLogicIOSTest.kt` | Modify | Replace or augment starter assertions with graph assembly and platform-binding smoke checks. |
| `core/designsystem/src/commonTest/kotlin/com/ketadev/foli/core/designsystem/FoliTokensTest.kt` | Create | Check representative compiled day/night values against the JSON source. |

The final number of Kotlin token files may be adjusted for clarity; no generated resource or runtime JSON loader is required.

## Interfaces / Contracts

```kotlin
// core:platform; illustrative contract, not a version-specific Koin API
interface PlatformInfo {
    val name: String
}

// shared; startup is native-owned and idempotent for this app process
fun initializeAndroidDependencies(/* Android context only if required */)
fun initializeIosDependencies()
```

`shared` owns the complete module list and platform-specific binding registration. Callers must invoke exactly one matching platform initializer before creating the UI. Isolated graph tests use the same module-list builder without mutating the process-global graph. A second app-owned initialization is a no-op; a graph owned by unrelated code is an error rather than being silently mistaken for Foli's graph.

No repository, DAO, persisted model, feature ViewModel, network service, or feature route is part of this contract. The root route, if introduced for Navigation compilation, only displays the existing starter app shell.

## Testing Strategy

| Layer | What to test | Approach |
|-------|--------------|----------|
| Gradle configuration | Five core modules, target variants, correct source-set placement | `./gradlew projects` and Android/iOS compile or dependency-resolution tasks; inspect graph for no `feature:*`. |
| Common unit | Representative day/night colors and dimensions, absence of runtime JSON dependency | `core:designsystem` common tests with exact expected values from `design/tokens/design-tokens.json`. |
| Android host | Shared + Android Koin modules resolve `PlatformInfo` and Compose ViewModel integration classpath | `:shared:testAndroidHostTest` using an isolated graph; verify duplicate initialization through a safe initializer test seam. |
| iOS simulator | Shared + iOS Koin modules resolve `PlatformInfo` | `:shared:iosSimulatorArm64Test` using an isolated graph. |
| App build / launch | Existing starter shell remains visible; startup does not fail | `:androidApp:assembleDebug`, iOS simulator build/test and manual native launches when tooling exists. Record exact commands/results or unavailable-toolchain limitations. |

The existing `commonTest`, `androidHostTest`, and `iosTest` source sets use `kotlin.test`; preserve that convention. Tests/framework presence does not itself select a TDD mode. During implementation, resolve the effective TDD setting from project/session configuration before the first behavior change.

## Threat Matrix

N/A — this change does not design routing of external inputs, shell/subprocess execution, VCS/PR automation, executable-file classification, or process integration. Typed in-app navigation routes do not cross that boundary.

## Migration / Rollout

No data migration or feature flag is required. This is additive infrastructure. Rollback removes the new modules, catalog aliases, and native startup hooks while retaining the original two-module starter. There is no user data to convert.

## Open Questions

- [ ] Which exact compatible versions and artifacts for Koin, Navigation, Room/KSP, Ktor, DataStore, and Compose ViewModel resolve with the repository's current Kotlin/AGP/Compose stack on both platforms? Verify against official release guidance and Gradle resolution during implementation; do not infer from unrelated templates.
- [ ] Are Young Serif and Nunito assets/licensing available for bundling? If not, keep explicit fallback fonts and defer exact font rendering without blocking compile-time color/dimension tokens.
