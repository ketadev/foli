package com.ketadev.foli

import com.ketadev.foli.core.platform.PlatformInfo
import com.ketadev.foli.di.androidPlatformModule
import com.ketadev.foli.di.foliModules
import com.ketadev.foli.di.resetFoliKoinForTest
import com.ketadev.foli.di.startFoliKoin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame
import kotlin.test.assertTrue
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.koinApplication

class SharedLogicAndroidHostTest {

    @AfterTest
    fun tearDown() {
        resetFoliKoinForTest()
    }

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun androidGraphResolvesPlatformInfoInIsolation() {
        val isolatedApp = koinApplication {
            modules(foliModules(androidPlatformModule))
        }

        val platformInfo = isolatedApp.koin.get<PlatformInfo>()
        assertTrue(platformInfo.name.isNotBlank())

        isolatedApp.close()
    }

    @Test
    fun repeatedAppOwnedStartupIsNoOp() {
        val first = startFoliKoin(androidPlatformModule)
        val second = startFoliKoin(androidPlatformModule)

        assertSame(first, second)
    }

    @Test
    fun startupFailsWhenAnUnrelatedKoinIsAlreadyRunning() {
        // Simulate a graph Foli did not start.
        startKoin { modules(emptyList<Module>()) }

        assertFailsWith<IllegalStateException> {
            startFoliKoin(androidPlatformModule)
        }
    }
}
