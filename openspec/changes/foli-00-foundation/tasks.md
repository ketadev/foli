# Tasks: FOLI-00 MVP Project Foundation

## Review Workload Forecast

| Field | Value |
|-------|-------|
| Estimated authored changed lines | 850–1,200 additions plus deletions, excluding generated files |
| 400-line budget risk | High |
| Chained PRs recommended | Yes |
| Suggested split | PR 1: core dependency foundation → PR 2: design system → PR 3: native-owned dependency assembly |
| Delivery strategy | ask-on-risk |
| Chain strategy | pending |

Decision needed before apply: Yes
Chained PRs recommended: Yes
Chain strategy: pending
400-line budget risk: High

The estimate covers five module build scripts, catalog and root Gradle edits, typed tokens, platform and Koin code, native startup, tests, and documentation. It is a planning estimate, not permission to compress code or omit tests. Before apply, choose `stacked-to-main` if each verified slice may land independently, or `feature-branch-chain` if the full foundation must integrate before main. A single over-budget PR requires explicit `size:exception`. Keep each work unit's tests and documentation in its work-unit commit; check the actual authored changed-line count before creating each PR. The split is subject to one honest review-budget adjustment, not repeated code shrinking.

### Suggested Work Units

| Unit | Goal and finish boundary | Likely PR | Focused test command | Runtime harness | Rollback boundary |
|------|--------------------------|-----------|----------------------|-----------------|-------------------|
| 1 | Register five compilable KMP core modules and resolve the catalogued storage, HTTP, state, navigation, and image-loading dependencies without feature behavior. | PR 1, about 300–400 lines | `./gradlew projects :core:data:compileKotlinAndroid :core:database:compileKotlinAndroid :core:data:compileKotlinIosSimulatorArm64 :core:database:compileKotlinIosSimulatorArm64` | N/A: this slice configures compile-time modules and intentionally has no executable service. | Revert the five `core/*/build.gradle.kts` files, their minimal source sets, and associated `settings.gradle.kts`, root `build.gradle.kts`, and catalog additions; the original two-module app remains. |
| 2 | Expose compiled day/night design tokens and consume them in the unchanged starter shell. | PR 2, about 250–350 lines | `./gradlew :core:designsystem:testAndroidHostTest :shared:testAndroidHostTest :core:designsystem:compileKotlinIosSimulatorArm64` | Launch the existing Android starter app and inspect its shell/theme; if device tooling is unavailable, record that limitation. | Revert `core/designsystem/src/`, its test, and the theme adapter in `shared/src/commonMain/kotlin/com/ketadev/foli/App.kt`, retaining unit 1's module registration. |
| 3 | Assemble isolated platform graphs, start Koin from native lifecycle owners, and prove Android/iOS entry points. | PR 3, about 300–450 lines; obtain `size:exception` if the cohesive slice cannot fit after one honest split | `./gradlew :shared:testAndroidHostTest :shared:iosSimulatorArm64Test :androidApp:assembleDebug` | Launch Android and Apple Silicon iOS simulator apps and recreate the shared shell without a second Koin startup; record exact results or unavailable tooling. | Revert `shared/src/*Main/kotlin/com/ketadev/foli/di/`, graph smoke tests, Android `FoliApplication`/manifest wiring, and Swift startup call; the starter shell and core modules remain. |

If `feature-branch-chain` is chosen, PR 1 targets a draft/no-merge tracker branch, PR 2 targets the PR 1 branch, and PR 3 targets the PR 2 branch. Retarget or rebase any child PR whose diff includes a previous unit. If `stacked-to-main` is chosen, merge verified slices to main in dependency order. No PR creation is part of this planning change.

## Phase 1: Core module and dependency foundation (Work Unit 1)

- [x] 1.1 Verify Koin, Ktor, Kotlin Serialization, Coroutines, typed Navigation Compose, Compose ViewModel, Room/KSP/SQLite, DataStore Preferences, and Coil 3 coordinates against official compatibility guidance for the existing Kotlin 2.4.20, AGP 9.1.1, and Compose 1.12.1 stack; pin only verified aliases in `gradle/libs.versions.toml` and required plugin aliases in `build.gradle.kts`. Confirm Android and iOS Gradle resolution; do not copy unverified versions from a template.
- [x] 1.2 Register exactly `:core:model`, `:core:data`, `:core:database`, `:core:designsystem`, and `:core:platform` in `settings.gradle.kts`. Verify `./gradlew projects` lists all five and no `:feature:*` project.
- [x] 1.3 Create `core/model/build.gradle.kts`, `core/data/build.gradle.kts`, `core/database/build.gradle.kts`, `core/designsystem/build.gradle.kts`, and `core/platform/build.gradle.kts` using the repository's Android Multiplatform Library and `iosArm64`/`iosSimulatorArm64` target pattern. Keep module-specific namespaces and only needed dependency edges; verify each Android and iOS compilation target resolves.
- [x] 1.4 Configure `core/database/build.gradle.kts` with Room KMP, SQLite, and KSP processors for applicable Android and iOS compilations, and `core/data/build.gradle.kts` with DataStore Preferences, Ktor Client, serialization, and Android/Darwin engines in their respective source sets. Prove compilation without adding a database class, entity, DAO, migration, repository, preference write, or network call.
- [x] 1.5 Add the required core project and common ViewModel/Koin/typed Navigation dependencies to `shared/build.gradle.kts`, with platform-only dependencies in platform source sets. Keep Coil 3 catalogued but not an active shared dependency. Verify Android/iOS shared compilation and inspect the dependency graph for no feature modules or external-service credentials.
- [x] 1.6 Record the exact Gradle dependency-resolution and compilation commands and results for this unit in the work-unit commit/PR evidence; include any limitation rather than claiming an unrun platform check passed.

## Phase 2: Typed design system (Work Unit 2)

- [x] 2.1 Create `core/designsystem/src/commonTest/kotlin/com/ketadev/foli/core/designsystem/FoliTokensTest.kt` with representative exact day/night color, spacing, radius, size, and typography assertions derived from `design/tokens/design-tokens.json` (read-only). Assert compiled values directly; separately inspect the module for no runtime JSON reader. If effective TDD is enabled, observe and record RED before implementation.
- [x] 2.2 Add typed Compose token values and palettes to `core/designsystem/src/commonMain/kotlin/com/ketadev/foli/core/designsystem/FoliTokens.kt`, splitting by token family only if clarity requires it. Map values from `design/tokens/design-tokens.json` (read-only); use explicit fallback fonts unless distributable Young Serif/Nunito assets and rights are confirmed. Make the token test GREEN.
- [x] 2.3 Apply a design-system theme adapter in `shared/src/commonMain/kotlin/com/ketadev/foli/App.kt` while retaining the existing starter content. Resolve Compose Material 3 and any root typed Navigation route on Android and iOS, without adding a feature screen or book-cover UI. Run the focused design-system and shared host tests and verify a build without packaged token JSON.
- [x] 2.4 Update `docs/architecture.md` with the compiled-token source and font-fallback boundary, keeping design documentation with the verified behavior. Record Android runtime inspection or the exact device-tooling limitation.

## Phase 3: Platform graph and native startup (Work Unit 3)

- [x] 3.1 Create `core/platform/src/commonMain/kotlin/com/ketadev/foli/core/platform/PlatformInfo.kt` and Android/iOS implementations in matching `core/platform/src/androidMain/` and `core/platform/src/iosMain/` packages. The service must be side-effect-free and identify the platform for smoke verification.
- [x] 3.2 Extend `shared/src/androidHostTest/kotlin/com/ketadev/foli/SharedLogicAndroidHostTest.kt` and `shared/src/iosTest/kotlin/com/ketadev/foli/SharedLogicIOSTest.kt` with isolated Koin graph tests resolving `PlatformInfo` and checking no duplicate/missing definitions. Add an Android-safe test seam for repeated app-owned initialization and rejection of an unrelated already-running graph. If effective TDD is enabled, observe and record RED before graph code.
- [x] 3.3 Create `shared/src/commonMain/kotlin/com/ketadev/foli/di/AppKoin.kt` to build the common module list separately from process-global startup and to guard one app-owned start. Create `shared/src/androidMain/kotlin/com/ketadev/foli/di/AppKoin.android.kt` and `shared/src/iosMain/kotlin/com/ketadev/foli/di/AppKoin.ios.kt` for their platform bindings and native-callable entry points. Confirm the selected Koin API and Swift export names against the resolved release; tests must be GREEN without opening a database, preferences file, or network connection.
- [x] 3.4 Create `androidApp/src/main/kotlin/com/ketadev/foli/FoliApplication.kt` and register it in `androidApp/src/main/AndroidManifest.xml`; call the Android graph initializer in `Application.onCreate`, not an Activity or Composable. Update `iosApp/iosApp/iOSApp.swift` to call the iOS initializer before `ContentView` creates the Compose controller. Confirm repeat shell creation does not restart Koin.
- [x] 3.5 Run and record exact results for `./gradlew :androidApp:assembleDebug`, `./gradlew :shared:testAndroidHostTest`, and `./gradlew :shared:iosSimulatorArm64Test`; also compile core iOS targets and launch both native apps when toolchains exist. Record each unavailable check as unavailable, not passed, and verify the existing starter shell remains visible without startup failure.
- [x] 3.6 Update `docs/architecture.md` with native-owned startup, core dependency direction, and the explicit absence of feature modules and external integrations. Keep test and documentation evidence with the work-unit commit; do not create PRs until the chain strategy and actual review budget are resolved.

## Apply prerequisites and boundaries

- Resolve the effective TDD mode from actual project/session configuration before source implementation. The current OpenSpec config does not specify `rules.apply.tdd` or a test runner, so test-first ordering above is not a claim that TDD is enabled.
- Confirm exact library/API compatibility and font asset availability during apply. Those are research and verification questions, not authorization to add feature behavior.
- The design threat matrix is explicitly N/A; no external-input, subprocess, VCS automation, or executable-classification RED case is required for this change.
- No source implementation, branch publication, PR, or remote operation is authorized by this planning artifact alone.
