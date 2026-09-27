# Foli MVP User Stories

**Status:** proposed backlog based on the [PRD](../foli-prd.md) and [architecture](../architecture.md). These stories do not imply that any feature has been implemented.

## How to use this backlog

- Each `FOLI-XX/` folder contains a `story.md` with a user outcome, acceptance criteria, and dependencies. Its stable ID can link the story to OpenSpec changes, tests, and, if needed, a task tracker.
- FOLI-00 is a technical enabler for the developer rather than a reader-facing feature. It sets up the shared and core foundation; create each `feature:*` module with the story that first needs it.
- **Core** delivers the cycle of reading and returning to read. **MVP expansion** completes the five pillars defined in the PRD. The suggested order does not remove any feature from the PRD's MVP scope.
- Open one OpenSpec change per story, or for a small group of stories that form a vertical flow. The change artifacts belong in `openspec/changes/`: cite the story IDs and link the relevant `story.md` in `proposal.md`, turn the acceptance criteria into scenarios in `specs/`, and break down implementation and tests in `tasks.md`. Technical tasks do not replace user stories.
- Cross-cutting rules apply to every story: Android and iOS support, private data stored on the device, no account requirement, the ability to start and complete reading sessions offline, and no requirement to save an idea or action after reading.

## Suggested order and dependencies

| Wave | Demonstrable outcome | Stories |
| --- | --- | --- |
| 0 | Shared and core modules and dependencies build on Android and iOS | FOLI-00 |
| Cross-cutting | A private and calm experience from the first flow | FOLI-21 |
| 1 | Add a book and see it in the library | FOLI-01 to FOLI-04 |
| 2 | Read, complete a session, and see progress offline | FOLI-05 to FOLI-10 |
| 3 | Return to reading with gentle motivation and reminders | FOLI-11 to FOLI-14 |
| 4 | Save and apply ideas optionally | FOLI-15 to FOLI-19 |
| 5 | Request AI suggestions if approved for the first release | FOLI-20 |

Catalog search (FOLI-02) can follow manual book entry, as the architecture proposes. FOLI-00 establishes the shared and core foundation and registers the MVP libraries; each feature module and its specific dependencies, persistence, navigation, design, platform adapters, and tests belong in the corresponding OpenSpec change's tasks. The `core/` modules are created in FOLI-00; the `feature/` modules are created incrementally.

## Technical foundation

- [FOLI-00 · Set up the MVP project foundation](FOLI-00/story.md) — Technical enabler.

## Library

- [FOLI-01 · Add a book manually](FOLI-01/story.md) — Core.
- [FOLI-02 · Search for and add a book from the catalog](FOLI-02/story.md) — MVP expansion.
- [FOLI-03 · Manage books to read and books in progress](FOLI-03/story.md) — Core.
- [FOLI-04 · Change a book's reading intention](FOLI-04/story.md) — MVP expansion.

## Reading and progress

- [FOLI-05 · Continue reading from the home screen](FOLI-05/story.md) — Core.
- [FOLI-06 · Choose a page goal](FOLI-06/story.md) — Core.
- [FOLI-07 · Read on a simple session screen](FOLI-07/story.md) — Core.
- [FOLI-08 · Complete a session and see the result](FOLI-08/story.md) — Core.
- [FOLI-09 · View book progress](FOLI-09/story.md) — Core.
- [FOLI-10 · Finish a book and view reading history](FOLI-10/story.md) — Core.

## Consistency and motivation

- [FOLI-11 · View the reading streak](FOLI-11/story.md) — MVP expansion.
- [FOLI-12 · Recover a lost streak](FOLI-12/story.md) — MVP expansion.
- [FOLI-13 · Earn small achievements](FOLI-13/story.md) — MVP expansion.
- [FOLI-14 · Set a considerate reminder](FOLI-14/story.md) — MVP expansion.

## Ideas and actions

- [FOLI-15 · Save an optional idea](FOLI-15/story.md) — MVP expansion.
- [FOLI-16 · View a book's ideas](FOLI-16/story.md) — MVP expansion.
- [FOLI-17 · Turn an idea into my own action](FOLI-17/story.md) — MVP expansion.
- [FOLI-18 · Track and evaluate an action](FOLI-18/story.md) — MVP expansion.
- [FOLI-19 · See a pending action on the home screen](FOLI-19/story.md) — MVP expansion.

## AI suggestion

- [FOLI-20 · Request an editable AI action suggestion](FOLI-20/story.md) — Conditional MVP expansion.

## Cross-cutting experience

- [FOLI-21 · Keep the experience private and calm](FOLI-21/story.md) — Cross-cutting.

## Decisions to resolve before writing the relevant OpenSpec changes

1. **Sessions:** what happens if someone leaves a session without recording pages, and how to prevent duplicate completion after an app interruption.
2. **Streaks:** the exact definition of a valid session, local dates when time zones change, and rules for editing or correcting sessions.
3. **Recovery:** challenge duration, deadline, eligibility, and behavior when the challenge fails. The PRD only gives a three-day example.
4. **Goals:** whether “today's goal” on the home screen is the last chosen goal or a separate daily preference. The PRD does not define how that goal persists or resets.
5. **Actions:** allowed state transitions, what “Applied” means, and when the final evaluation appears.
6. **AI:** whether FOLI-20 is included in the first release, the provider, intermediary service, usage limits, and data handling. Manual action creation does not depend on this decision.

These decisions do not prevent work on the first stories. Resolve each one in the OpenSpec change that introduces the corresponding rule, before implementing and testing it.

## Outside this backlog

Widgets, voice notes, OCR, photographed quotes, flashcards, global idea search, intelligent review, knowledge maps, advanced statistics, book recommendations, community features, device synchronization, web and desktop apps, and AI access to full book content belong to later releases under §23 of the PRD.
