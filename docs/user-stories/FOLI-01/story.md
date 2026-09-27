# FOLI-01 · Add a book manually — Core

**As a** reader, **I want to** add a book even when it is not in the catalog or I am offline, **so that** I can start reading without relying on an external search.

**Acceptance criteria**

- I can save the title, author, and total page count; the cover is optional and a placeholder appears when it is missing.
- I can choose one of three reading intentions: Just read, Read and remember, or Read and apply.
- The book remains available after restarting the app. I do not need an account or an internet connection.
- A nonpositive total page count is rejected, and missing required fields are explained.

**Depends on:** local book storage. **PRD:** §§6, 8.2, 22.
---

Sources: [PRD](../../foli-prd.md) · [Architecture](../../architecture.md) · [MVP index](../README.md).
