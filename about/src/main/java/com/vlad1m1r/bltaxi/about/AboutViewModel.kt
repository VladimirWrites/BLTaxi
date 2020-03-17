package com.vlad1m1r.bltaxi.about

import androidx.lifecycle.ViewModel
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.di.AppInfoProvider
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.usecase.ExecuteAction

class AboutViewModel(
    private val executeAction: ExecuteAction,
    private val appInfoProvider: AppInfoProvider,
    private val stringResolver: StringResolver
) : ViewModel() {

    val appVersionName = appInfoProvider.getVersionName()

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
