package com.vlad1m1r.bltaxi.about.ui

import androidx.lifecycle.ViewModel
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class AboutViewModel @Inject constructor(
    private val executeAction: ExecuteAction,
    private val appInfoProvider: AppInfoProvider,
    private val stringResolver: StringResolver
) : ViewModel() {

    fun getAppVersionName() = appInfoProvider.getVersionName()

    fun writeEmail() {
        executeAction(
            Action.SendEmailAction(stringResolver.getString(R.string.about__email))
        )
    }

    fun rateApp() {
        executeAction(
            Action.OpenPlayStoreAction(appInfoProvider.getApplicationId())
        )
    }

    fun shareApp() {
        executeAction(
            Action.ShareAction(stringResolver.getString(R.string.about__play_store_url))
        )
    }

    fun openPrivacyPolicy() {
        executeAction(
            Action.OpenUrlAction(stringResolver.getString(R.string.about__privacy_policy_url))
        )
    }

    fun openTermsAndConditions() {
        executeAction(
            Action.OpenUrlAction(stringResolver.getString(R.string.about__terms_and_conditions_url))
        )
    }
}
