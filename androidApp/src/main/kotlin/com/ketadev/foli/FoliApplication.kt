package com.ketadev.foli

import android.app.Application
import com.ketadev.foli.di.initializeAndroidDependencies

/**
 * Foli's Android [Application]: the single, process-level owner of Koin
 * startup. `MainActivity` and its Compose content never start Koin
 * themselves, since Activities can be recreated multiple times per process.
 */
class FoliApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeAndroidDependencies(this)
    }
}
