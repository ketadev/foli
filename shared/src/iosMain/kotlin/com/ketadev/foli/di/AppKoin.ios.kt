package com.ketadev.foli.di

import com.ketadev.foli.core.platform.IosPlatformInfo
import com.ketadev.foli.core.platform.PlatformInfo
import org.koin.dsl.module

/**
 * iOS platform bindings. FOLI-00 only binds [PlatformInfo].
 */
val iosPlatformModule = module {
    single<PlatformInfo> { IosPlatformInfo() }
}

/**
 * Starts Foli's Koin graph from the iOS app lifecycle. Callable from Swift.
 *
 * MUST be called exactly once, from the SwiftUI `@main App` initializer,
 * before `ContentView` creates the Compose `UIViewController` — never from
 * the `UIViewControllerRepresentable` creation callback, which SwiftUI can
 * invoke again. See [startFoliKoin] for the no-op/error contract on repeated
 * or conflicting startup.
 *
 * Returns `Unit` on purpose: Swift only needs the side effect.
 */
fun initializeIosDependencies() {
    startFoliKoin(iosPlatformModule)
}
