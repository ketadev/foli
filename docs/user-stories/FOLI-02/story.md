# FOLI-02 · Search for and add a book from the catalog — MVP expansion

**As a** reader, **I want to** search for a book by title or author, **so that** I can add it with less typing.

**Acceptance criteria**

- Available results show the cover, title, author, and page count when the catalog provides them.
- Before saving, I can correct the details and choose a reading intention; the book keeps its own local identifier.
- If there are no results, the page count is missing, or the network fails, I can continue with manual entry.
- Searching requires an internet connection, but a saved book remains available offline.

**Depends on:** FOLI-01. **PRD:** §8.2. **Architecture:** `BookCatalog`, with Google Books as the initial catalog.
---

Sources: [PRD](../../foli-prd.md) · [Architecture](../../architecture.md) · [MVP index](../README.md).
