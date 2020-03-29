package com.vlad1m1r.bltaxi.settings

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.databinding.ObservableInt
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.preference.PreferenceManager
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

    lateinit var sharedPreferences: SharedPreferences

    lateinit var keyAnalytics: String
    lateinit var keyCrashReport: String

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(testApplication.applicationContext)
        keyAnalytics = testApplication.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_analytics)
        keyCrashReport = testApplication.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_crash_reports)
    }

    @Test
    fun disableAnalytics_whenClickedOnAnalyticSwitchAndAnalyticsEnabled() {
        sharedPreferences.edit()
            .putBoolean(keyAnalytics, true)
            .commit()
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnAnalyticsSwitch() }

        verify(fragmentViewModel).enableTracking(false)
    }

    @Test
    fun enableAnalytics_whenClickedOnAnalyticSwitchAndAnalyticsDisabled() {
        sharedPreferences.edit()
            .putBoolean(keyAnalytics, false)
            .commit()
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnAnalyticsSwitch() }

        verify(fragmentViewModel).enableTracking(true)
    }

    @Test
    fun disableCrashReporting_whenClickedOnCrashReportingSwitchAndCrashReportingEnabled() {
        sharedPreferences.edit()
            .putBoolean(keyCrashReport, true)
            .commit()
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnCrashReportingSwitch() }

        verify(fragmentViewModel).enableCrashReport(false)
    }

    @Test
    fun enableCrashReporting_whenClickedOnCrashReportingSwitchAndCrashReportingDisabled() {
        sharedPreferences.edit()
            .putBoolean(keyCrashReport, false)
            .commit()
        launchFragmentInContainer<SettingsFragment>()

        settingsScreen { clickOnCrashReportingSwitch() }

        verify(fragmentViewModel).enableCrashReport(true)
    }
}