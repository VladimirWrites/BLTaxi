package com.vlad1m1r.bltaxi.settings.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class SettingsViewModelShould {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val analyticsKey = "analytics"
    private val crashKey = "crash_reports"
    private val themeKey = "theme_picker"

    private val stringResolver = mock<StringResolver> {
        on { getString(R.string.theme_value_dark) }.thenReturn("dark")
        on { getString(R.string.theme_value_light) }.thenReturn("light")
        on { getString(R.string.theme_value_default) }.thenReturn("default")
        on { getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_analytics) }.thenReturn(analyticsKey)
        on { getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_crash_reports) }.thenReturn(crashKey)
        on { getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_theme_picker) }.thenReturn(themeKey)
    }
    private val crashReport = mock<CrashReport>()
    private val tracker = mock<Tracker>()

    private val settingsViewModel = SettingsViewModel(context, stringResolver, crashReport, tracker)

    private val prefs get() = PreferenceManager.getDefaultSharedPreferences(context)

    @Test
    fun enableCrashReport() {
        settingsViewModel.sendAction(SettingsAction.CrashReportToggled(true))

        verify(crashReport).enableCrashReporting(true)
        assertThat(settingsViewModel.state.value.isCrashReportEnabled).isTrue()
        assertThat(prefs.getBoolean(crashKey, false)).isTrue()
    }

    @Test
    fun disableCrashReport() {
        settingsViewModel.sendAction(SettingsAction.CrashReportToggled(false))

        verify(crashReport).enableCrashReporting(false)
        assertThat(settingsViewModel.state.value.isCrashReportEnabled).isFalse()
        assertThat(prefs.getBoolean(crashKey, true)).isFalse()
    }

    @Test
    fun enableTracking() {
        settingsViewModel.sendAction(SettingsAction.AnalyticsToggled(true))

        verify(tracker).enableTracking(true)
        assertThat(settingsViewModel.state.value.isAnalyticsEnabled).isTrue()
        assertThat(prefs.getBoolean(analyticsKey, false)).isTrue()
    }

    @Test
    fun disableTracking() {
        settingsViewModel.sendAction(SettingsAction.AnalyticsToggled(false))

        verify(tracker).enableTracking(false)
        assertThat(settingsViewModel.state.value.isAnalyticsEnabled).isFalse()
        assertThat(prefs.getBoolean(analyticsKey, true)).isFalse()
    }

    @Test
    fun changeTheme_toDark() {
        settingsViewModel.sendAction(SettingsAction.ThemeChanged("dark"))

        assertThat(settingsViewModel.state.value.nightMode).isEqualTo(MODE_NIGHT_YES)
        assertThat(settingsViewModel.state.value.selectedTheme).isEqualTo("dark")
        assertThat(prefs.getString(themeKey, null)).isEqualTo("dark")
    }

    @Test
    fun changeTheme_toLight() {
        settingsViewModel.sendAction(SettingsAction.ThemeChanged("light"))

        assertThat(settingsViewModel.state.value.nightMode).isEqualTo(MODE_NIGHT_NO)
        assertThat(settingsViewModel.state.value.selectedTheme).isEqualTo("light")
    }

    @Test
    fun changeTheme_toDefault() {
        settingsViewModel.sendAction(SettingsAction.ThemeChanged("default"))

        assertThat(settingsViewModel.state.value.nightMode).isEqualTo(MODE_NIGHT_FOLLOW_SYSTEM)
        assertThat(settingsViewModel.state.value.selectedTheme).isEqualTo("default")
    }

    @Test
    fun fallBackToSystemTheme_whenThemeIsUnsupported() {
        settingsViewModel.sendAction(SettingsAction.ThemeChanged("unsupported_value"))

        assertThat(settingsViewModel.state.value.nightMode).isEqualTo(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun initializeStateFromPreferences() {
        prefs.edit()
            .putString(themeKey, "dark")
            .putBoolean(analyticsKey, false)
            .putBoolean(crashKey, false)
            .apply()

        val viewModel = SettingsViewModel(context, stringResolver, crashReport, tracker)

        assertThat(viewModel.state.value.selectedTheme).isEqualTo("dark")
        assertThat(viewModel.state.value.isAnalyticsEnabled).isFalse()
        assertThat(viewModel.state.value.isCrashReportEnabled).isFalse()
    }
}
