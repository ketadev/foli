# MVP Stack Availability Specification

## Purpose

Make the MVP technology stack resolvable for the shared foundation and later features while keeping platform dependencies in their appropriate source sets.

## ADDED Requirements

### Requirement: Version-catalog ownership

`gradle/libs.versions.toml` MUST own compatible versions and aliases for foundation plugins and libraries used by the Android and iOS targets. The foundation MUST declare dependencies in the consuming module and appropriate source set rather than making platform-only libraries common dependencies.

#### Scenario: Foundation dependencies resolve

- GIVEN the version catalog and core modules are configured
- WHEN Android and iOS foundation targets resolve their dependencies
- THEN the required plugins and libraries resolve without version conflicts that prevent compilation.

#### Scenario: Platform-only dependency placement

- GIVEN a dependency requires an Android or iOS platform API
- WHEN the common and platform source-set dependencies are inspected
- THEN that dependency is declared only for its applicable platform target.

### Requirement: Shared UI and state stack

The shared UI foundation MUST resolve Compose Multiplatform, Material 3, multiplatform ViewModel integration, Coroutines and Flow, and typed Navigation Compose routes for Android and iOS consumers.

#### Scenario: Shared UI target compiles

- GIVEN the shared app shell depends on the UI and state foundation
- WHEN Android and iOS shared UI targets are compiled
- THEN those targets can resolve the named UI, state, and navigation APIs.

### Requirement: Design tokens without runtime JSON parsing

`core:designsystem` MUST expose consumable Compose design values derived from the existing design tokens. App startup and rendering MUST NOT require parsing `design/tokens/design-tokens.json` at runtime.

#### Scenario: Design values are consumed

- GIVEN a shared Compose consumer depends on `core:designsystem`
- WHEN it compiles against a value represented in the existing design tokens
- THEN that value is available through the design-system API.

#### Scenario: Token source file is unavailable at runtime

- GIVEN the app package does not contain the design-token JSON file
- WHEN the starter app launches and renders
- THEN design-system values remain available without a runtime parse or file-read failure.

### Requirement: Storage and preferences stack

The data foundation MUST resolve Room Kotlin Multiplatform with SQLite and its KSP compiler, and DataStore Preferences Kotlin Multiplatform, for applicable Android and iOS targets. FOLI-00 MUST NOT introduce feature entities, repositories, migrations, or persisted user data.

#### Scenario: Storage dependencies compile

- GIVEN the foundation has no feature schema or repository
- WHEN Android and iOS data and database targets are compiled
- THEN Room, SQLite, KSP-generated code support, and DataStore Preferences dependencies resolve for their applicable targets.

#### Scenario: No premature persisted behavior

- GIVEN FOLI-00 is the only implemented story
- WHEN storage module source and generated schema artifacts are inspected
- THEN no feature entity, migration, or persisted user-data behavior is present.

### Requirement: HTTP, serialization, and image-loading readiness

The data foundation MUST resolve Ktor Client, Kotlin Serialization, and compatible Android and iOS engines in their platform source sets. Coil 3 for Compose Multiplatform MUST be available in the version catalog for a later book-cover feature, but FOLI-00 MUST NOT implement book-cover UI or remote API behavior.

#### Scenario: HTTP stack resolves on both platforms

- GIVEN Ktor and serialization are configured in the foundation
- WHEN Android and iOS data targets compile
- THEN their applicable Ktor engines and serialization APIs resolve.

#### Scenario: Image loading is catalogued but not implemented

- GIVEN the version catalog is inspected after FOLI-00
- WHEN Coil 3 aliases are located
- THEN a later Compose Multiplatform feature can declare Coil from the catalog
- AND no book-cover UI or feature module exists in the foundation.
