package com.vlad1m1r.bltaxi.about.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import dagger.hilt.android.lifecycle.HiltViewModel
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
class AboutViewModel @Inject constructor(
    private val executeAction: ExecuteAction,
    private val appInfoProvider: AppInfoProvider,
    private val stringResolver: StringResolver
) : ViewModel() {

    // Actions flow - UI sends actions to ViewModel
    private val _actions = MutableSharedFlow<AboutAction>()
    private val actions: SharedFlow<AboutAction> = _actions.asSharedFlow()

    // State flow - ViewModel exposes state to UI
    private val _state = MutableStateFlow(AboutState())
    val state: StateFlow<AboutState> = _state.asStateFlow()

    // Effects flow - One-time events
    private val _effects = MutableSharedFlow<AboutEffect>()
    val effects: SharedFlow<AboutEffect> = _effects.asSharedFlow()

    init {
        initializeState()
        observeActions()
    }

    private fun initializeState() {
        _state.update { it.copy(appVersion = appInfoProvider.getVersionName()) }
    }

    private fun observeActions() {
        viewModelScope.launch {
            actions.collect { action ->
                handleAction(action)
            }
        }
    }

    fun sendAction(action: AboutAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun handleAction(action: AboutAction) {
        when (action) {
            AboutAction.SendEmailClicked -> {
                executeAction(
                    Action.SendEmailAction(stringResolver.getString(R.string.about__email))
                )
            }
            AboutAction.RateAppClicked -> {
                executeAction(
                    Action.OpenPlayStoreAction(appInfoProvider.getApplicationId())
                )
            }
            AboutAction.ShareAppClicked -> {
                executeAction(
                    Action.ShareAction(stringResolver.getString(R.string.about__play_store_url))
                )
            }
            AboutAction.PrivacyPolicyClicked -> {
                executeAction(
                    Action.OpenUrlAction(stringResolver.getString(R.string.about__privacy_policy_url))
                )
            }
            AboutAction.TermsAndConditionsClicked -> {
                executeAction(
                    Action.OpenUrlAction(stringResolver.getString(R.string.about__terms_and_conditions_url))
                )
            }
        }
    }

    // Legacy methods for backward compatibility with existing Fragment/tests
    // TODO: Remove after migrating to Compose
    @Deprecated("Use sendAction(AboutAction.SendEmailClicked) instead")
    fun getAppVersionName() = appInfoProvider.getVersionName()

    @Deprecated("Use sendAction(AboutAction.SendEmailClicked) instead")
    fun writeEmail() {
        sendAction(AboutAction.SendEmailClicked)
    }

    @Deprecated("Use sendAction(AboutAction.RateAppClicked) instead")
    fun rateApp() {
        sendAction(AboutAction.RateAppClicked)
    }

    @Deprecated("Use sendAction(AboutAction.ShareAppClicked) instead")
    fun shareApp() {
        sendAction(AboutAction.ShareAppClicked)
    }

    @Deprecated("Use sendAction(AboutAction.PrivacyPolicyClicked) instead")
    fun openPrivacyPolicy() {
        sendAction(AboutAction.PrivacyPolicyClicked)
    }

    @Deprecated("Use sendAction(AboutAction.TermsAndConditionsClicked) instead")
    fun openTermsAndConditions() {
        sendAction(AboutAction.TermsAndConditionsClicked)
    }
}
