package com.vlad1m1r.bltaxi.settings.ui

data class SettingsState(
    val selectedTheme: String = "",
    val isAnalyticsEnabled: Boolean = false,
    val isCrashReportEnabled: Boolean = false,
    val nightMode: Int = Int.MIN_VALUE
)
