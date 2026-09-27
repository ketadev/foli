# Foli

**Reading a little today makes it easier to come back tomorrow.** Foli is a mobile app for Android and iOS that helps people build a reading habit through short sessions, self-chosen page goals, and visible progress. When a book sparks a useful thought, readers can also save an idea and turn it into an action.

## Why Foli?

Getting started is often harder than reading a few pages. Books pile up, progress goes unnoticed, and too much tracking can make reading feel like another chore. Foli aims to shorten the path from **“I want to read”** to **“I'm reading”**: open the app, pick up a book, record your progress, and have a gentle reason to return the next day.

The priority is consistency, not time spent in the app. The experience is designed to be personal, calm, and private, with no rankings or public activity.

## Key features

### 📚 Your library, your pace

Add books manually or find them in a catalog. Organize your reading list and keep up to two books active at a time. For each book, choose whether you want to *just read*, *read and remember*, or *read and apply*.

### 📖 Reading sessions that are easy to start

Set a page goal, read without distractions, and record where you stopped. When you finish a session, you immediately see how much progress you've made in the book.

### 🌱 Motivation to return

Build consistency with streaks, small achievements, and considerate reminders. If you break a streak, you can recover it without making your return to reading feel like a punishment.

### 💭 Ideas worth keeping

After reading, save an idea or reflection if something stands out. This step is optional: your reading session is already complete.

### ⚡ From an idea to an action

When you want to put something you've learned into practice, turn an idea into a concrete action and track the outcome. An editable AI suggestion may help you define it, while you make the final decision.

The core journey stays simple: **choose a book → set a goal → read → record your progress → come back to read again**.

## Technology and architecture

Foli uses **Kotlin Multiplatform** to share logic between Android and iOS, and **Compose Multiplatform + Material 3** for the UI. The proposed MVP architecture organizes code into feature modules (`library`, `reading`, `streaks`, `ideas`, `actions`) and core modules for models, data, the database, design, and platform adapters. `shared` assembles navigation and dependencies; `androidApp` and `iosApp` are the native entry points.

| Need | Planned choice |
| --- | --- |
| State and asynchronous work | Multiplatform ViewModel, Coroutines, and Flow |
| Navigation | Multiplatform Navigation Compose with typed routes |
| Local data | Room KMP with SQLite; DataStore Preferences for small settings |
| Catalog and networking | Google Books behind an app-owned interface; Ktor Client and Kotlin Serialization |
| Book covers | Coil 3 |
| Dependency injection | Koin, with shared definitions and platform-specific adapters |
| Reminders | Local notifications through native adapters |
| Design | Tokens in [`design/tokens/design-tokens.json`](design/tokens/design-tokens.json) |

> **Repository status:** `androidApp`, `shared`, and the five `core` foundation modules are configured. Feature modules and product flows remain proposed.

For full decisions and constraints, see the [architecture document](docs/architecture.md). For the product problem, flows, and scope, see the [PRD](docs/foli-prd.md).

## Run the current project

- **Android:** run `./gradlew :androidApp:assembleDebug` or launch `androidApp` from your IDE.
- **iOS:** open [`iosApp/iosApp.xcodeproj`](iosApp/iosApp.xcodeproj) in Xcode and run the app.

## Quality checks and tests

- `./gradlew ktlintCheck` checks Kotlin formatting; use `./gradlew ktlintFormat` to format code intentionally.
- `./gradlew detekt` runs static analysis across the Kotlin modules.
- `./gradlew :shared:testAndroidHostTest` runs shared Android host tests, which can use MockK. Common and iOS tests use `kotlin.test`; MockK is not available in those source sets.
- `./gradlew :shared:iosSimulatorArm64Test` runs shared iOS simulator tests on a configured macOS host.

The lint and static-analysis tasks report existing source violations; adding the tools does not silently baseline or suppress those findings.
