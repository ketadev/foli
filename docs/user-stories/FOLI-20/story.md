# FOLI-20 · Request an editable AI action suggestion — Conditional MVP expansion

**As a** reader, **I want to** request a suggestion based on my idea, **so that** I can formulate a more concrete action when I need help.

**Acceptance criteria**

- A suggestion is requested only when I explicitly choose to do so, and the service receives only the selected idea's text, never the book's content.
- I can edit, accept, or ignore the suggestion; it is saved as an action only after I accept it.
- If I am offline, the service fails, or I do not want to use AI, I can create the action manually and continue.
- The provider does not require a private key embedded in the app; if it is remote, it uses the intermediary service and usage limits defined for release.

**Depends on:** FOLI-17 and the decision to include AI in the release. **PRD:** §16. **Architecture:** whether to include a remote provider in the first public release is still undecided.
---

Sources: [PRD](../../foli-prd.md) · [Architecture](../../architecture.md) · [MVP index](../README.md).
