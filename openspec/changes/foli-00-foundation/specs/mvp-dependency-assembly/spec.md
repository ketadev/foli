# MVP Dependency Assembly Specification

## Purpose

Ensure the shared foundation can be assembled from either native entry point and that cross-platform verification is recorded honestly.

## ADDED Requirements

### Requirement: Shared and platform dependency graph

Koin MUST provide shared definitions and the Compose ViewModel integration needed by the app shell, with Android and iOS contributions kept in their respective platform contexts. Graph assembly MUST NOT require accounts, synchronization, remote AI, a deployed backend, or a feature module.

#### Scenario: Android graph assembles

- GIVEN the Android platform contributions are available
- WHEN the shared graph is assembled for Android
- THEN its foundation definitions resolve without missing or duplicate definitions.

#### Scenario: iOS graph assembles

- GIVEN the iOS platform contributions are available
- WHEN the shared graph is assembled for iOS
- THEN its foundation definitions resolve without missing or duplicate definitions.

#### Scenario: Future integrations are absent

- GIVEN only the FOLI-00 foundation is configured
- WHEN either platform graph is assembled
- THEN it does not require feature services or external-service credentials.

### Requirement: Single startup per platform entry point

Each native app entry point MUST start the shared Koin graph once during app startup. Repeated entry into the shared app shell MUST NOT attempt a second graph startup or fail because Koin is already running.

#### Scenario: First launch initializes dependency injection

- GIVEN Koin has not started for the app process
- WHEN either native entry point launches the app shell
- THEN the shared graph starts once with that platform's contributions.

#### Scenario: Repeated app-shell creation does not restart Koin

- GIVEN the native entry point has started the graph
- WHEN the shared app shell is created again in the same process
- THEN no second Koin startup occurs
- AND the app shell remains usable.

### Requirement: Foundation verification evidence

The foundation MUST include a minimal graph assembly verification or smoke test for both platform configurations. Implementation evidence MUST include the exact command and result for `:androidApp:assembleDebug`, `:shared:testAndroidHostTest`, and `:shared:iosSimulatorArm64Test`, and MUST record any unavailable platform check rather than representing it as passed.

#### Scenario: Required checks pass on a configured host

- GIVEN the Android SDK and Apple Silicon iOS simulator tooling are available
- WHEN the three required Gradle checks run
- THEN each command completes successfully
- AND the Android and iOS graph verification outcomes are recorded.

#### Scenario: Platform tooling is unavailable

- GIVEN a required platform toolchain is unavailable
- WHEN its required check is attempted or diagnosed
- THEN the exact command and observed failure or limitation are recorded
- AND that check is not reported as passing.

#### Scenario: Native launch verification

- GIVEN both platform toolchains are available
- WHEN the starter app is launched on Android and iOS after the foundation change
- THEN each platform displays the existing starter app shell without dependency-injection startup failure.
