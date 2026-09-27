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
- [ ] AG-2: Commit the verified documentation as one Conventional Commit. Checks: clean scope, commit identity recorded, native RDD assessed per project policy.

## Progress
AG-1 verified with `git diff --check`, config/path checks, and direct documentation review. No Kotlin source changed; Gradle checks not applicable. Branch: `codex/agent-guidelines` from synchronized `main` at `06fdf69`.

## Next step
Commit the documentation work unit and record its identity and RDD outcome.
