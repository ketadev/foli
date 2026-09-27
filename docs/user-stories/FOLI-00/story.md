# FOLI-00 · Set up the MVP project foundation — Technical enabler

**As a** Foli developer, **I want to** configure the shared foundation, libraries, and platform dependencies defined in the architecture, **so that** each MVP feature module can be added and tested on Android and iOS when its story is developed.

**Acceptance criteria**

- `settings.gradle.kts` includes the proposed `core:model`, `core:data`, `core:database`, `core:designsystem`, and `core:platform` modules. Each is configured as a Kotlin Multiplatform module with the Android and iOS targets it needs. `shared` remains the app shell and both native app entry points remain connected.
- No `feature:*` module or placeholder is created in FOLI-00. Each feature module is created and added to `settings.gradle.kts` as part of the first story that implements that feature.
- `gradle/libs.versions.toml` owns compatible versions and aliases for the required MVP plugins and libraries. Dependencies used by the foundation are declared in the appropriate existing modules and source sets; feature-specific dependencies are declared when their feature modules are created.
- The shared UI foundation resolves Compose Multiplatform, Material 3, multiplatform ViewModel, Coroutines and Flow, and typed Navigation Compose routes. The design system module can consume the existing design tokens without requiring runtime JSON parsing.
- The data foundation resolves Room KMP with SQLite and KSP for its compiler, DataStore Preferences KMP, and Ktor Client with the Kotlin Serialization plugin and compatible Android/iOS engines. Coil 3 for Compose Multiplatform is available in the version catalog for the first feature that displays book covers. Platform-specific dependencies stay in their corresponding source sets.
- Koin is configured for shared definitions, Compose ViewModel integration, and Android/iOS contributions, and starts once from each platform entry point. A minimal graph verification or smoke test confirms that the shared graph can be assembled on both platforms.
- The existing starter app still builds and launches on Android and iOS after the core module setup. The Android debug build, shared Android host tests, and Apple Silicon iOS simulator tests complete successfully on a host with the required tooling; any platform-specific limitation is recorded with the exact command and result.
- The setup does not require accounts, synchronization, a remote AI provider, or a deployed backend. Those integrations are introduced only by the stories that use them.

**Depends on:** none; complete before FOLI-01 and other feature stories. **Architecture:** module structure, MVP stack, dependency injection, and implementation sequence. **PRD:** §5.

---

Sources: [PRD](../../foli-prd.md) · [Architecture](../../architecture.md) · [MVP index](../README.md).
