package com.ketadev.foli.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

/**
 * Common Koin definitions shared by every platform.
 *
 * Kept intentionally empty for FOLI-00: no repository, database, network, or
 * feature binding belongs here yet. It exists so [foliModules] has a stable,
 * always-present first entry to extend as later stories add shared
 * definitions.
 */
val commonModule: Module = module {}

/**
 * Builds the complete module list the app assembles on every platform:
 * [commonModule] plus the platform-specific [platformModule] (which must, at
 * minimum, bind [com.ketadev.foli.core.platform.PlatformInfo]).
 *
 * This function only builds the list; it starts nothing and never touches
 * Koin's global context, so production code and isolated test graphs can
 * share the exact same module composition.
 */
fun foliModules(platformModule: Module): List<Module> = listOf(commonModule, platformModule)

/**
 * Foli's app-owned Koin instance, once started by [startFoliKoin]. `null`
 * before startup or after [resetFoliKoinForTest] runs.
 *
 * Thread-safety assumption: [startFoliKoin] is only ever called once per
 * process, from a platform's native application-lifecycle entry point
 * (Android `Application.onCreate`, iOS `@main App.init`) — both of which run
 * on the main thread before any UI is created, and before any other code in
 * this app could race a second call. This variable is therefore a plain,
 * unsynchronized reference rather than an atomic/locked one. If a future
 * story introduces a second legitimate caller (e.g. background pre-warming
 * from a non-main thread), add explicit synchronization at that time instead
 * of assuming this comment still holds.
 */
private var appOwnedKoinApplication: KoinApplication? = null

/**
 * Starts Foli's Koin graph exactly once per process, combining
 * [commonModule] with [platformModule] and applying [appDeclaration] (used
 * by platform initializers to add bindings such as `androidContext`).
 *
 * - If Foli already started Koin in this process, this call is a no-op: it
 *   returns the existing app-owned [KoinApplication] without restarting
 *   anything.
 * - If Koin's global context already holds an application Foli did not
 *   start (for example a host app, another library, or a leaked test
 *   context), this throws [IllegalStateException] instead of silently
 *   adopting a graph it does not own.
 */
internal fun startFoliKoin(
    platformModule: Module,
    appDeclaration: KoinAppDeclaration = {},
): KoinApplication {
    appOwnedKoinApplication?.let { return it }

    check(KoinPlatformTools.defaultContext().getOrNull() == null) {
        "Koin is already started by another owner. startFoliKoin only " +
            "manages Foli's own app-owned graph and will not adopt an " +
            "unrelated Koin instance; stop the existing instance first if " +
            "this is intentional."
    }

    val started = startKoin {
        appDeclaration()
        modules(foliModules(platformModule))
    }
    appOwnedKoinApplication = started
    return started
}

/**
 * Test-only seam: clears Foli's app-owned Koin reference and stops the
 * global Koin context (if any), so host and simulator tests that exercise
 * [startFoliKoin] remain order-independent. Not part of the production
 * startup contract.
 */
internal fun resetFoliKoinForTest() {
    appOwnedKoinApplication = null
    if (KoinPlatformTools.defaultContext().getOrNull() != null) {
        stopKoin()
    }
}
