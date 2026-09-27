# MVP Module Foundation Specification

## Purpose

Define the shared project structure that later MVP stories can extend without introducing feature implementations prematurely.

## ADDED Requirements

### Requirement: Core module registration

The project MUST register `core:model`, `core:data`, `core:database`, `core:designsystem`, and `core:platform` in `settings.gradle.kts`. Each core module MUST expose Kotlin Multiplatform targets and source sets appropriate to its Android and iOS consumers.

#### Scenario: All foundation modules are available

- GIVEN the project is checked out with the FOLI-00 foundation
- WHEN the Gradle project list is evaluated
- THEN each of the five named core modules is present
- AND each can be resolved by its consuming Android and iOS targets.

#### Scenario: Feature modules are deferred

- GIVEN only FOLI-00 has been implemented
- WHEN the Gradle project list and project directories are inspected
- THEN no `feature:*` module or placeholder exists.

### Requirement: App entry points remain connected

The project MUST retain `shared` as the Compose app shell and MUST keep the Android and iOS native entry points connected to it after the core modules are added.

#### Scenario: Android app shell remains accessible

- GIVEN the foundation modules are registered
- WHEN the Android app is built and launched
- THEN it opens the existing starter app shell.

#### Scenario: iOS app shell remains accessible

- GIVEN the foundation modules are registered
- WHEN the iOS app is built and launched in an Apple Silicon simulator
- THEN it opens the existing starter app shell.

### Requirement: Foundation responsibility boundaries

The five core modules MUST provide separate places for shared models, data access, database support, design-system values, and platform integration. Foundation modules MUST NOT require a `feature:*` module, account service, synchronization service, remote AI provider, or deployed backend to build and resolve.

#### Scenario: Foundation compiles without future features

- GIVEN no feature modules or external service configuration exist
- WHEN the shared Android and iOS targets resolve the core modules
- THEN their compilation dependencies resolve without those future features or services.

#### Scenario: Later feature is not scaffolded early

- GIVEN a dependency is only needed by a later feature story
- WHEN the FOLI-00 module graph is inspected
- THEN no feature module or feature-specific behavior has been added solely to host it.
