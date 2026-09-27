package com.ketadev.foli.core.data

/**
 * Marks `core:data` as the home for repository contracts (e.g.
 * `BookRepository`, `ReadingRepository`) and their local/remote
 * implementations, backed by `core:database`, DataStore Preferences, and
 * Ktor.
 *
 * FOLI-00 wires the dependency stack only; no repository, preference write,
 * or network call is implemented yet. This marker only proves the module
 * compiles for Android and iOS targets.
 */
internal object CoreDataMarker
