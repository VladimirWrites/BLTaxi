package com.vlad1m1r.bltaxi.settings.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stringResolver: StringResolver,
    private val crashReport: CrashReport,
    private val tracker: Tracker
) : ViewModel() {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Actions flow - UI sends actions to ViewModel
    private val _actions = MutableSharedFlow<SettingsAction>()
    private val actions: SharedFlow<SettingsAction> = _actions.asSharedFlow()

    // State flow - ViewModel exposes state to UI
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    // Effects flow - One-time events
    private val _effects = MutableSharedFlow<SettingsEffect>()
    val effects: SharedFlow<SettingsEffect> = _effects.asSharedFlow()

    init {
        initializeState()
        observeActions()
    }

    private fun initializeState() {
        val analyticsKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_analytics)
        val crashKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_crash_reports)
        val themeKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_theme_picker)

        val defaultTheme = stringResolver.getString(R.string.theme_value_default)

        _state.update {
            it.copy(
                selectedTheme = prefs.getString(themeKey, defaultTheme) ?: defaultTheme,
                isAnalyticsEnabled = prefs.getBoolean(analyticsKey, true),
                isCrashReportEnabled = prefs.getBoolean(crashKey, true)
            )
        }
    }

    private fun observeActions() {
        viewModelScope.launch {
            actions.collect { action ->
                handleAction(action)
            }
        }
    }

    fun sendAction(action: SettingsAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun handleAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ThemeChanged -> {
                changeTheme(action.theme)
            }
            is SettingsAction.AnalyticsToggled -> {
                enableTracking(action.enabled)
            }
            is SettingsAction.CrashReportToggled -> {
                enableCrashReport(action.enabled)
            }
        }
    }

    private fun changeTheme(theme: String) {
        val newMode = when (theme) {
            stringResolver.getString(R.string.theme_value_dark) -> MODE_NIGHT_YES
            stringResolver.getString(R.string.theme_value_light) -> MODE_NIGHT_NO
            stringResolver.getString(R.string.theme_value_default) -> MODE_NIGHT_FOLLOW_SYSTEM
            else -> MODE_NIGHT_FOLLOW_SYSTEM
        }

        // Update preference
        val themeKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_theme_picker)
        prefs.edit().putString(themeKey, theme).apply()

        // Update state
        _state.update { it.copy(selectedTheme = theme, nightMode = newMode) }

        // Emit effect
        viewModelScope.launch {
            _effects.emit(SettingsEffect.UpdateNightMode(newMode))
        }
    }

    private fun enableCrashReport(enabled: Boolean) {
        crashReport.enableCrashReporting(enabled)

        val crashKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_crash_reports)
        prefs.edit().putBoolean(crashKey, enabled).apply()

        _state.update { it.copy(isCrashReportEnabled = enabled) }
    }

    private fun enableTracking(enabled: Boolean) {
        tracker.enableTracking(enabled)

        val analyticsKey = stringResolver.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_analytics)
        prefs.edit().putBoolean(analyticsKey, enabled).apply()

        _state.update { it.copy(isAnalyticsEnabled = enabled) }
    }
}
