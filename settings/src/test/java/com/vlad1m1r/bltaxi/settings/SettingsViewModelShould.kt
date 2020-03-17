package com.vlad1m1r.bltaxi.settings

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import org.junit.Test

class SettingsViewModelShould {

    private val stringResolver = mock<StringResolver>()
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
    fun changeTheme() {
    }
}
