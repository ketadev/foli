package com.ketadev.foli.di

import android.content.Context
import com.ketadev.foli.core.platform.AndroidPlatformInfo
import com.ketadev.foli.core.platform.PlatformInfo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android platform bindings. FOLI-00 only binds [PlatformInfo].
 */
val androidPlatformModule = module {
    single<PlatformInfo> { AndroidPlatformInfo() }
}

/**
 * Starts Foli's Koin graph from the Android application lifecycle.
 *
 * MUST be called exactly once, from [android.app.Application.onCreate] —
 * never from an `Activity` or a `@Composable`, which can be recreated
 * multiple times per process. See [startFoliKoin] for the no-op/error
 * contract on repeated or conflicting startup.
 *
 * Returns `Unit` on purpose: callers only need the side effect, and this
 * keeps `org.koin.core.KoinApplication` out of `androidApp`'s compile
 * classpath, which only declares an `implementation` dependency on `shared`.
 */
fun initializeAndroidDependencies(context: Context) {
    startFoliKoin(androidPlatformModule) {
        androidContext(context)
    }
}
