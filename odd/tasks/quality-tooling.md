# Quality tooling

## Objective
Configure ktlint, detekt, and MockK for the Foli Kotlin Multiplatform project.

## Problem and scope
The project has no ktlint or detekt Gradle tasks and no MockK dependency. Apply lint and static analysis to Kotlin modules; use MockK only in Android/JVM host tests because MockK is not a common/iOS test library. Do not change application behavior or add iOS mocking.

## Constraints
- Branch: `codex/quality-tooling`, based on the locally fetched `origin/main`.
- TDD mode: not configured; ordinary functional checks. Runner: Gradle wrapper.
- Route: delegated direct. Mapping needs more than four Gradle/module files; implementation spans multiple non-trivial Gradle files.
- Delivery strategy: ask-on-risk. Forecast: under 400 authored changed lines.

## Tasks
- [x] QT-1: Add centrally versioned ktlint, detekt, and MockK coordinates and apply/configure analysis across Kotlin modules. Acceptance observed: Gradle registers analysis tasks; MockK resolves for Android host tests only; generated code is excluded. Checks: `:shared:tasks --all` and `:shared:testAndroidHostTest` passed; `ktlintCheck detekt --continue` ran but failed on existing checked-in-source findings. Commit: pending.
- [x] QT-2: Document quality/test commands and report platform verification. Acceptance observed: README lists commands and source-set scope. Checks: `:shared:iosSimulatorArm64Test` passed with `DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer`; `git diff --check` passed. Commit: pending.

## Progress
Implementation and applicable checks complete. Existing source lint/static-analysis findings remain unsuppressed and are outside this configuration task. Next: commit and report the check failures.
