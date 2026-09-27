package com.ketadev.foli.core.platform

/**
 * Identifies the current runtime platform.
 *
 * Implementations MUST be side-effect-free: they only read information the
 * platform already exposes (OS name, OS version) and never perform I/O,
 * request permissions, or touch storage/network. FOLI-00 uses this contract
 * purely as a real, minimal binding that proves the Koin graph wires
 * Android/iOS-specific definitions correctly; later stories may replace or
 * extend it without changing that verification shape.
 */
interface PlatformInfo {
    val name: String
}
