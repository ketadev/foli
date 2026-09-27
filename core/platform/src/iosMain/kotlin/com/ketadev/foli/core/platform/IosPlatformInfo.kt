package com.ketadev.foli.core.platform

import platform.UIKit.UIDevice

/**
 * iOS [PlatformInfo], identifying the platform via [UIDevice.currentDevice]'s
 * system name and version (e.g. "iOS 17.4"). Reading those properties is
 * side-effect-free.
 */
class IosPlatformInfo : PlatformInfo {
    override val name: String =
        "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion()}"
}
