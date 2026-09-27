# Proposal: FOLI-00 MVP Project Foundation

## Intent

Turn the current two-module starter into the Android/iOS foundation required by FOLI-00, so later feature stories can add independently testable modules without first rebuilding the app shell or dependency setup.

## Scope

### In Scope
- Add `core:model`, `core:data`, `core:database`, `core:designsystem`, and `core:platform` as Kotlin Multiplatform modules with appropriate Android and iOS targets; retain `shared` and both native entry points.
- Establish the version-catalog plugins and libraries, source-set dependencies, design-token consumption, and non-feature scaffolding for the MVP stack named in FOLI-00.
- Assemble shared and platform Koin definitions and start the graph once per platform; verify graph assembly and the existing starter app on both platforms.

### Out of Scope
- `feature:*` modules or placeholders, book-cover UI, feature repositories, domain entities, migrations, and production feature behavior.
- Accounts, synchronization, remote AI, a deployed backend, and integrations owned by later stories.

## Capabilities

### New Capabilities
- `mvp-module-foundation`: The five core KMP modules, permitted dependency direction, and preserved shared/native app composition.
- `mvp-stack-availability`: Compatible version-catalog and source-set setup for UI, state, navigation, storage, preferences, HTTP/serialization, image loading, and design tokens.
- `mvp-dependency-assembly`: Shared and platform Koin initialization, graph verification, and Android/iOS build-test evidence.

### Modified Capabilities
None. `openspec/specs/` has no existing capability specs.

## Approach

Add the five responsibility-specific core modules without introducing a generic common or premature feature module. Keep `shared` as the Compose app shell and graph assembler. Put platform APIs and Ktor engines in their platform source sets, keep domain-facing code in common source sets, and expose design tokens as Compose values rather than parsing JSON at runtime. Pin compatible library and plugin versions in `gradle/libs.versions.toml`; confirm compatibility with both targets during design and implementation. Start Koin from each native entry point through a shared initializer that prevents duplicate startup.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `settings.gradle.kts`, `gradle/libs.versions.toml` | Modified | Register modules and pin foundation plugins/libraries. |
| `core/{model,data,database,designsystem,platform}/` | New | KMP modules and bounded foundation wiring. |
| `shared/`, `androidApp/`, `iosApp/` | Modified | App-shell dependencies and platform Koin startup. |
| `design/tokens/design-tokens.json` | Input | Source for compiled design-system values; no runtime JSON dependency. |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Kotlin, AGP, Compose, Room/KSP, and other library versions may not be mutually compatible for both targets. | Medium | Verify official compatibility guidance, resolve Gradle dependencies, and run Android/iOS checks before claiming success. |
| iOS startup or native source-set dependencies may diverge from Android. | Medium | Test graph initialization and an iOS simulator build/test on an Apple host; record exact limitations and commands if unavailable. |
| Empty infrastructure can become premature feature implementation. | Medium | Restrict scaffolding to usable foundation contracts and smoke tests; defer feature behavior to its story. |

## Rollback Plan

Revert the foundation change's commits, removing the five Gradle includes/modules and their catalog and app-shell wiring. The existing `androidApp` and `shared` starter remains the baseline; do not alter user data or add migrations in this change.

## Dependencies

- No preceding user story. Implementation precedes FOLI-01 and other feature stories.
- Android SDK and an Apple Silicon macOS host with Xcode/iOS simulator tooling for the full verification matrix.

## Success Criteria

- [ ] All five core modules are included, target Android and iOS as needed, and no `feature:*` module exists.
- [ ] The named MVP stack resolves from the version catalog in appropriate source sets, with Coil 3 catalogued for later feature use and design tokens available without runtime parsing.
- [ ] Koin starts once from each native entry point and a graph smoke test assembles shared definitions on both platforms.
- [ ] The starter app builds and launches on Android and iOS; `:androidApp:assembleDebug`, `:shared:testAndroidHostTest`, and `:shared:iosSimulatorArm64Test` pass, or any unavailable platform check records its exact command and result.
