package com.vlad1m1r.bltaxi.settings

import android.app.Application
import android.os.Build
import androidx.databinding.ObservableInt
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_App)
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(emptyList())
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApplication::class)
class SettingsFragmentShould {

    lateinit var testApplication: TestApplication

    val modeStub = ObservableInt(Int.MIN_VALUE)

    val fragmentViewModel = mock<SettingsViewModel>() {
        on { mode }.thenReturn(modeStub)
    }

    @Before
    fun before() {
        testApplication =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        testApplication.injectModule(
            module {
                single {
                    fragmentViewModel
                }
            }
        )
    }

    @Test
    fun enableAnalytics_whenClickedOnAnalyticSwitch() {
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnAnalyticsSwitch() }

        verify(fragmentViewModel).enableTracking(true)
    }

    @Test
    fun enableCrashReporting_whenClickedOnCrashReportingSwitch() {
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnCrashReportingSwitch() }

        verify(fragmentViewModel).enableCrashReport(true)
    }
}