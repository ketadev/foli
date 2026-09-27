# Repository Guidelines

This file is the single source of repository instructions for coding agents. Other agent entry points should link here rather than duplicate rules. Verify version numbers against configuration before changing dependencies or platform targets.

## Project and Stack

Foli is an offline-first Kotlin Multiplatform reading-habit app for Android and iOS. Shared UI uses Compose Multiplatform and Material 3; the iOS entry point uses SwiftUI. The package root is `com.ketadev.foli`.

| Item | Current configuration | Source of truth |
| --- | --- | --- |
| Kotlin | 2.4.20 | `gradle/libs.versions.toml` |
| Android Gradle Plugin | 9.1.1 | `gradle/libs.versions.toml` |
| Compose Multiplatform / Material 3 | 1.12.1 / 1.12.0-alpha03 | `gradle/libs.versions.toml` |
| Android compile SDK / target SDK / minimum SDK | 37 / 37 / 24 | `gradle/libs.versions.toml` |
| iOS minimum deployment target | 18.2 | `iosApp/iosApp.xcodeproj/project.pbxproj` |
| Swift language version | 5.0 | `iosApp/iosApp.xcodeproj/project.pbxproj` |

The project configures `iosArm64` and `iosSimulatorArm64` Kotlin targets. The version catalog also pins Koin 4.2.2, Navigation Compose 2.9.2, Room 2.8.5, SQLite 2.6.2, DataStore 1.2.1, Coil 3.6.3, Ktor 3.6.0, coroutines and serialization 1.11.0, KSP 2.3.12, MockK 1.14.11, ktlint Gradle plugin 14.2.0, and detekt 2.0.0-alpha.6. A catalog entry does not mean a library is already used by a module; inspect its Gradle dependencies before relying on it.

Prefer stable library releases. Use alpha or other prerelease versions only when a needed feature or compatibility requires them, the risk is understood, and the Kotlin, Compose, AGP, KSP, and platform versions work together. Do not upgrade versions in isolation; verify the affected build and tests.

## Repository Map and References

`androidApp/` and `iosApp/` are the native entry points. `shared/` is the shared composition shell and platform bridge. The configured `core/` modules are `model`, `data`, `database`, `designsystem`, and `platform`; feature modules in the architecture document are planned, not yet configured. Keep common behavior in `commonMain` and platform APIs in matching platform source sets. Register new modules in `settings.gradle.kts` and dependency versions in `gradle/libs.versions.toml`.

Read the relevant source before implementing:

| Need | Reference |
| --- | --- |
| Product behavior and scope | `docs/foli-prd.md` |
| Architecture and intended module boundaries | `docs/architecture.md` |
| Delivery order and open domain decisions | `docs/user-stories/README.md` |
| Story acceptance criteria | `docs/user-stories/FOLI-XX/story.md` (use the applicable story) |
| Colors, typography, spacing, radii | `design/tokens/design-tokens.json` |
| Screen references | `assets/*.jpg` and `assets/Screens.pdf` |
| Active capability specifications | `openspec/specs/` |

The documents describe some target-state behavior not yet implemented. Check current modules and source before treating a design as existing code. The design-token JSON informs compiled Compose theme values; do not load it at runtime.

## Kotlin and Swift Conventions

- Use four-space indentation and follow nearby formatting. Types use `PascalCase`; functions and properties use `camelCase`; test classes use descriptive `*Test` names.
- Keep domain models and rules independent of UI, database entities, and network DTOs. Prefer immutable state, explicit nullability, and small functions with clear responsibilities.
- Use `expect`/`actual` or interfaces only at genuine platform boundaries. Keep platform APIs out of `commonMain` and do not add abstractions that merely forward a call.
- Name composables for the UI they render. Hoist state when a parent owns it; keep side effects in appropriate Compose effect APIs rather than during composition.
- Follow the configured ktlint formatting and detekt rules. Do not suppress findings merely to pass checks; explain any narrowly scoped exception.

## Build and Verification

- `./gradlew :androidApp:assembleDebug` builds Android.
- `./gradlew :shared:testAndroidHostTest` runs shared Android host tests; `./gradlew :shared:iosSimulatorArm64Test` runs shared iOS simulator tests on a configured macOS host.
- `./gradlew :shared:allTests` runs all configured shared targets when the required Apple tooling is available.
- After modifying Kotlin files, run `./gradlew ktlintFormat`, inspect its diff so unrelated files are not reformatted, then run `./gradlew ktlintCheck`. There is no configured task named `kotlinCheck`; `ktlintCheck` is the formatting check.
- Run `./gradlew detekt` and the affected build/tests for Kotlin behavior changes. Report pre-existing findings separately from new failures; do not claim a check passed when it did not.
- Open `iosApp/iosApp.xcodeproj` in Xcode for the iOS app. Android Studio can run `androidApp` directly.

Tests use `kotlin.test` and `@Test`. Put platform-independent tests in `commonTest` and platform-specific tests in the matching source set. Add regression tests for changed domain rules, including reading progress. No coverage threshold is configured.

## Commits and Pull Requests

Use Conventional Commits, not Gitmoji: `type(scope): imperative summary` (scope optional). Use `feat` for features, `fix` for fixes, `docs` for documentation, `test` for tests, `refactor` for behavior-preserving changes, `build` for build/dependency changes, `ci` for CI changes, and `chore` for maintenance. Examples: `feat(reading): track session progress`, `ci: run Kotlin checks`, `docs: centralize agent guidelines`. Keep commits focused and do not add AI attribution or `Co-Authored-By` trailers.

In pull requests, describe the user-visible change, list exact checks and outcomes, link a related issue when one exists, and include screenshots for UI changes.

## Local Configuration

Keep machine-specific Android SDK settings in ignored `local.properties`. Never commit credentials or service keys. Update architecture documentation when adding an external integration.
