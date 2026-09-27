package com.ketadev.foli.core.model

/**
 * Marks `core:model` as the home for domain models and rules shared across
 * feature modules (e.g. `Book`, `ReadingSession`, `Idea`, `Action`).
 *
 * This module intentionally has no UI or infrastructure dependencies. Domain
 * types are added by the feature stories that define their behavior; this
 * marker only proves the module compiles for Android and iOS targets.
 */
internal object CoreModelMarker
