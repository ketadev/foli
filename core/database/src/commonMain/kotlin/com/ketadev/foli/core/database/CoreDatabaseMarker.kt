package com.ketadev.foli.core.database

/**
 * Marks `core:database` as the home for Room storage: the database class,
 * entities, DAOs, and migrations.
 *
 * FOLI-00 configures Room, SQLite, and the KSP compiler for Android and iOS
 * without defining any schema yet; the feature stories that need persistence
 * add entities and migrations. This marker only proves the module compiles
 * for Android and iOS targets.
 */
internal object CoreDatabaseMarker
