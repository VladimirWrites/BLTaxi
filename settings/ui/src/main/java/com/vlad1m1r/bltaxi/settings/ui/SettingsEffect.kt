package com.vlad1m1r.bltaxi.settings.ui

sealed interface SettingsEffect {
    data class UpdateNightMode(val mode: Int) : SettingsEffect
}
