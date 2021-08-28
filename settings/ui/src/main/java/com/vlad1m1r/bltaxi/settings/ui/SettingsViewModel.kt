package com.vlad1m1r.bltaxi.settings.ui

import androidx.appcompat.app.AppCompatDelegate.*
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val stringResolver: StringResolver,
    private val crashReport: CrashReport,
    private val tracker: Tracker
) : ViewModel() {

    val mode = ObservableInt(Int.MIN_VALUE)

    fun enableCrashReport(enabled: Boolean) {
        crashReport.enableCrashReporting(enabled)
    }

    fun enableTracking(enabled: Boolean) {
        tracker.enableTracking(enabled)
    }

    fun changeTheme(theme: String) {
        val newMode = when (theme) {
            stringResolver.getString(R.string.theme_value_dark) -> MODE_NIGHT_YES
            stringResolver.getString(R.string.theme_value_light) -> MODE_NIGHT_NO
            stringResolver.getString(R.string.theme_value_default) -> MODE_NIGHT_FOLLOW_SYSTEM
            else -> throw IllegalArgumentException("Mode with value: $theme is not supported")
        }
        mode.set(newMode)
    }
}
