# Repository Guidelines

## Project Structure & Module Organization

Foli is a Kotlin Multiplatform mobile app. `shared/src/commonMain` holds the current Compose UI and shared logic; `shared/src/androidMain` and `shared/src/iosMain` hold platform code. Tests live in the matching `shared/src/commonTest`, `androidHostTest`, and `iosTest` source sets. `androidApp/` and `iosApp/` are the native entry points. `assets/` contains reference screens, `design/tokens/design-tokens.json` defines design values, and `docs/` contains the product and architecture plans. Only `:androidApp` and `:shared` are configured today; the `core/` and `feature/` modules in `docs/architecture.md` are proposed, not present.

## Build, Test, and Development Commands

- `./gradlew :androidApp:assembleDebug` builds the Android debug APK.
- `./gradlew :shared:testAndroidHostTest` runs shared Android host tests.
- `./gradlew :shared:iosSimulatorArm64Test` runs Kotlin tests on an Apple Silicon iOS simulator.
- `./gradlew :shared:allTests` runs tests for all configured targets; use a macOS host with the required Apple tooling.
- Open `iosApp/iosApp.xcodeproj` in Xcode to build and run the iOS app. Android Studio can run `androidApp` directly.

## Coding Style & Naming Conventions

Use four-space indentation in Kotlin and Swift, with standard Kotlin naming: `PascalCase` types, `camelCase` functions and properties, and descriptive `*Test` classes. Keep shared behavior in `commonMain`; place platform API calls in the relevant platform source set. Define dependency versions in `gradle/libs.versions.toml`. Follow nearby source formatting; the repository does not currently configure a dedicated formatter or linter.

## Testing Guidelines

Tests use `kotlin.test` and `@Test`. Put platform-independent rules in `commonTest` and platform behavior in its matching test source set. Name tests for the behavior they verify, and add regression tests when changing reading progress or other domain rules. No coverage threshold is configured. Run the relevant test task before opening a pull request.

## Commit & Pull Request Guidelines

Recent commits use short imperative subjects, often with Gitmoji codes such as `:memo:` for documentation and `:bento:` for assets. Keep commits focused. In pull requests, describe the user-visible change, list the Gradle or Xcode checks run, link a related issue when one exists, and include screenshots for UI changes.

## Local Configuration

Keep machine-specific Android SDK settings in ignored `local.properties`. Do not commit credentials or service keys; update the architecture documentation when adding a new external integration.
