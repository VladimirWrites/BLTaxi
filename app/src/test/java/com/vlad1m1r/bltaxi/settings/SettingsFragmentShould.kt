package com.vlad1m1r.bltaxi.settings

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.databinding.ObservableInt
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.di.AnalyticsModule
import com.vlad1m1r.bltaxi.launchFragmentInHiltContainer
import dagger.hilt.android.testing.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = HiltTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
@UninstallModules(AnalyticsModule::class)
class SettingsFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val stringResolver = mock<StringResolver>()

    @BindValue
    val tracker = mock<Tracker>()

    @BindValue
    val crashReport = mock<CrashReport>()

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var keyAnalytics: String
    private lateinit var keyCrashReport: String

    @Before
    fun before() {
        val testApplication = ApplicationProvider.getApplicationContext<Application>()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(testApplication.applicationContext)
        keyAnalytics = testApplication.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_analytics)
        keyCrashReport = testApplication.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_crash_reports)
    }

    @Test
    fun disableAnalytics_whenClickedOnAnalyticSwitchAndAnalyticsEnabled() {
        sharedPreferences.edit()
            .putBoolean(keyAnalytics, true)
            .commit()
        launchFragmentInHiltContainer<SettingsFragment>()

        settingsScreen { clickOnAnalyticsSwitch() }

        verify(tracker).enableTracking(false)
    }

    @Test
    fun enableAnalytics_whenClickedOnAnalyticSwitchAndAnalyticsDisabled() {
        sharedPreferences.edit()
            .putBoolean(keyAnalytics, false)
            .commit()
        launchFragmentInHiltContainer<SettingsFragment>()

        settingsScreen { clickOnAnalyticsSwitch() }

        verify(tracker).enableTracking(true)
    }

    @Test
    fun disableCrashReporting_whenClickedOnCrashReportingSwitchAndCrashReportingEnabled() {
        sharedPreferences.edit()
            .putBoolean(keyCrashReport, true)
            .commit()
        launchFragmentInHiltContainer<SettingsFragment>()

        settingsScreen { clickOnCrashReportingSwitch() }

        verify(crashReport).enableCrashReporting(false)
    }

    @Test
    fun enableCrashReporting_whenClickedOnCrashReportingSwitchAndCrashReportingDisabled() {
        sharedPreferences.edit()
            .putBoolean(keyCrashReport, false)
            .commit()
        launchFragmentInHiltContainer<SettingsFragment>()

        settingsScreen { clickOnCrashReportingSwitch() }

        verify(crashReport).enableCrashReporting(true)
    }
}