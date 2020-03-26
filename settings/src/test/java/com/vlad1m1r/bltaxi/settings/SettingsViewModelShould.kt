package com.vlad1m1r.bltaxi.settings

import androidx.appcompat.app.AppCompatDelegate.*
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import org.junit.Test

class SettingsViewModelShould {

    private val stringResolver = mock<StringResolver> {
        on { getString(R.string.theme_value_dark) }.thenReturn("dark")
        on { getString(R.string.theme_value_light) }.thenReturn("light")
        on { getString(R.string.theme_value_default) }.thenReturn("default")
    }
    private val crashReport = mock<CrashReport>()
    private val tracker = mock<Tracker>()

    val settingsViewModel = SettingsViewModel(stringResolver, crashReport, tracker)

    @Test
    fun enableCrashReport() {
        settingsViewModel.enableCrashReport(true)
        verify(crashReport).enableCrashReporting(true)
    }

    @Test
    fun disableCrashReport() {
        settingsViewModel.enableCrashReport(false)
        verify(crashReport).enableCrashReporting(false)
    }

    @Test
    fun enableTracking() {
        settingsViewModel.enableTracking(true)
        verify(tracker).enableTracking(true)
    }

    @Test
    fun disableTracking() {
        settingsViewModel.enableTracking(false)
        verify(tracker).enableTracking(false)
    }

    @Test
    fun changeTheme_toDark() {
        settingsViewModel.changeTheme("dark")
        assertThat(settingsViewModel.mode.get()).isEqualTo(MODE_NIGHT_YES)
    }

    @Test
    fun changeTheme_toLight() {
        settingsViewModel.changeTheme("light")
        assertThat(settingsViewModel.mode.get()).isEqualTo(MODE_NIGHT_NO)
    }

    @Test
    fun changeTheme_toDefault() {
        settingsViewModel.changeTheme("default")
        assertThat(settingsViewModel.mode.get()).isEqualTo(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test(expected = IllegalArgumentException::class)
    fun changeTheme_toUnsupported() {
        settingsViewModel.changeTheme("unsupported_value")
    }
}
