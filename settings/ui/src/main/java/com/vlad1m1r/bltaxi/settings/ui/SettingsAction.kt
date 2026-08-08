package com.vlad1m1r.bltaxi.settings.ui

sealed interface SettingsAction {
    data class ThemeChanged(val theme: String) : SettingsAction
    data class AnalyticsToggled(val enabled: Boolean) : SettingsAction
    data class CrashReportToggled(val enabled: Boolean) : SettingsAction
}
