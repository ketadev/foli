# Agent guidelines

## Objective
Make `AGENTS.md` the single source of repository instructions and reduce `CLAUDE.md` to a pointer.

## Scope and constraints
- Document verified current stack, versions, platform targets, Kotlin conventions, checks, dependency policy, commit format, and key references.
- Distinguish configured dependencies from planned architecture; derive mutable versions from config files.
- Authorized: documentation changes only. No remote operations.
- TDD: not configured (existing ODD quality-tooling record); use documentation checks. Runner: Gradle wrapper for task verification.
- Route: delegated exploration (configuration and references span 4+ files), inline writer (one non-trivial file; `CLAUDE.md` pointer is mechanical).
- Delivery: `ask-on-risk`; forecast ~200 authored lines, one work-unit commit.

## Tasks
- [x] AG-1: Update `AGENTS.md` and replace duplicated `CLAUDE.md` instructions with a pointer. Acceptance: facts match current config, requested policies are explicit, references exist. Checks: inspect diff, verify paths/versions/commands.
- [x] AG-2: Commit the verified documentation as one Conventional Commit. Checks: clean scope, commit identity recorded, native RDD assessed per project policy.

## Progress
AG-1 verified with `git diff --check`, config/path checks, and direct documentation review. No Kotlin source changed; Gradle checks not applicable. Branch: `codex/agent-guidelines` from synchronized `main` at `06fdf69`.

## Commit and review
- Work unit: `6935b63` (`docs: centralize agent guidelines`); rollback boundary: `AGENTS.md`, `CLAUDE.md`, and this task document.
- Functional check: documentation diff, referenced paths, and version/config values verified. Runtime harness: N/A (documentation-only). Gradle checks skipped because no Kotlin code changed.
- Native RDD assessment: medium, `under_budget` (131 changed lines); review not due.

## Next step
Await user decision on pushing or opening a PR; no remote operation authorized.
