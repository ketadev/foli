package com.ketadev.foli.core.platform

import android.os.Build

/**
 * Android [PlatformInfo], identifying the platform via [Build.VERSION.SDK_INT].
 * Reading that field is side-effect-free.
 */
class AndroidPlatformInfo : PlatformInfo {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}
