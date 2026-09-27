# FOLI-08 · Complete a session and see the result — Core

**As a** reader, **I want to** enter the last page I read and see the result immediately, **so that** I can feel my progress without doing the math.

**Acceptance criteria**

- I enter an ending page greater than the starting page and no greater than the book's total page count; an invalid value does not change the data.
- On confirmation, the session is saved and the book's current page is updated as one operation. Repeating the confirmation does not duplicate progress.
- I see pages read in the session and the book's previous and new percentages. If a streak exists, its updated value is shown.
- The session is complete before any invitation to save an idea appears; I can leave without writing anything.
- Completion works offline and persists after restarting the app.

**Depends on:** FOLI-07. **PRD:** §§10, 11. **Architecture:** transactional session completion.
---

Sources: [PRD](../../foli-prd.md) · [Architecture](../../architecture.md) · [MVP index](../README.md).
