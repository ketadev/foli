package com.ketadev.foli.core.platform

/**
 * Marks `core:platform` as the home for interfaces and Android/iOS adapters
 * over native platform APIs (e.g. clock, notifications, permissions).
 *
 * FOLI-00 adds no platform contract yet; `PlatformInfo` and its
 * implementations are introduced in the native-startup work unit. This
 * marker only proves the module compiles for Android and iOS targets.
 */
internal object CorePlatformMarker
