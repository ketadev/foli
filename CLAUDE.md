# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project state

Foli is a Kotlin Multiplatform (Android + iOS) reading-habit app using Compose Multiplatform + Material 3. **The repo is still the KMP starter template**: only `:androidApp` and `:shared` exist, and `shared` holds the template screen (`App.kt`, `Greeting`, `Platform` expect/actual). Everything described in `docs/architecture.md` (the `core/*` and `feature/*` modules, Room, Koin, Ktor, Coil, Navigation, DataStore) is the *target* design, not existing code. Don't assume those modules or dependencies exist; when adding them, register the module in `settings.gradle.kts` and pin versions in `gradle/libs.versions.toml`.

Package root is `com.ketadev.foli`. `AGENTS.md` has the repo conventions (style, commits, PRs), which also apply here.

## Commands

- Build Android debug: `./gradlew :androidApp:assembleDebug`
- Shared tests on the JVM (fastest): `./gradlew :shared:testAndroidHostTest`
- Single test class: `./gradlew :shared:testAndroidHostTest --tests "com.ketadev.foli.SharedCommonTest"` (common tests run as part of each target's test task)
- iOS simulator tests (Apple Silicon): `./gradlew :shared:iosSimulatorArm64Test`
- All targets: `./gradlew :shared:allTests` (needs macOS + Xcode)
- iOS app: open `iosApp/iosApp.xcodeproj` in Xcode. `iosApp` consumes `shared` as a static framework named `Shared` through `MainViewController.kt`.

No linter or formatter is configured. Tests use `kotlin.test`.

## Target architecture (from `docs/architecture.md`)

Read that document before structural work. The main points:

- **Module graph:** `androidApp/iosApp → shared → feature:* → core:model + core:data + core:designsystem`; `shared → core:data → core:database`; platform APIs are reached through interfaces in `core:platform`. Dependencies only point that way. Features never import another feature's internals, and there is no generic `core:common`.
- `shared` becomes a thin composition shell (theme, typed navigation routes, Koin setup, `App()`). Feature logic lives in feature modules.
- **Data:** Room KMP (tables `Book`, `ReadingSession`, `Idea`, `Action`) behind repository contracts in `core:data`. Repositories expose `Flow` for reads and `suspend` for writes. Domain types stay separate from Room entities and network DTOs. Google Books sits behind `BookCatalog`, and manual entry must keep working without it.
- **Use cases only when they add something:** a rule, a transformation, orchestration, or a transaction (e.g. `CompleteReadingSession`). A use case that just passes one call to a repository should not exist; the ViewModel can call the repository directly.
- **Domain invariants to enforce in use cases, not only in the UI:** at most 2 active books; page validation; closing a session and updating progress happen in one transaction; the streak is *derived* from days with valid sessions, not stored as a counter; at most one `Action` per `Idea`; ideas and actions are always optional and never block closing a session.
- Offline-first and no account for the MVP. Use stable app-owned IDs (never catalog IDs or auto-increment) so sync can be added later, but don't add sync tables or state yet.
- AI suggestions (`ActionSuggestionService`) are optional and editable. The app must never embed a provider API key.
- **Design tokens:** `design/tokens/design-tokens.json` (day and night themes) is the source for colors, type, spacing and radii. Translate it into Compose theme types in `core:designsystem`. Don't read the JSON at runtime.

## Product docs and workflow

- The docs are in **Spanish**: `docs/foli-prd.md` (PRD), `docs/architecture.md`, and `docs/user-stories/FOLI-XX/story.md` (acceptance criteria and PRD section references). `docs/user-stories/README.md` gives the suggested delivery waves and lists open domain decisions (session abandonment, streak/timezone rules, streak recovery, goal persistence, action state transitions). Settle the relevant decision inside the change that introduces that rule.
- `assets/*.jpg` / `Screens.pdf` are the reference wireframes for each screen (named in Spanish, e.g. `04_sesion_de_lectura.jpg`).
- Changes follow **OpenSpec** (`openspec/`, skills in `.claude/skills/openspec-*`, commands `/opsx:*`). Open one change per story or per small vertical slice of stories. `proposal.md` cites the FOLI IDs, `specs/` turns acceptance criteria into scenarios, and `tasks.md` lists implementation and test work.
- Commits use short imperative subjects with Gitmoji codes (`:memo:`, `:bento:`, `:fire:`, …).
