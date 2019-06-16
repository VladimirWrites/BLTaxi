package com.vlad1m1r.bltaxi.about

import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.baseui.BaseViewModel
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.about.di.AppInfoProvider
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.interactor.ActionInteractor

class AboutViewModel(
    private val actionInteractor: ActionInteractor,
    private val appInfoProvider: AppInfoProvider,
    private val stringResolver: StringResolver,
    dispatchers: CoroutineDispatcherProvider
) : BaseViewModel(dispatchers.main) {

    val appVersionName = appInfoProvider.getVersionName()

    fun writeEmail() {
        actionInteractor.execute(
            Action.SendEmailAction("write@vladimirj.dev")
        )
    }

    fun rateApp() {
        actionInteractor.execute(
            Action.OpenPlayStoreAction(appInfoProvider.getApplicationId())
        )
    }

    fun shareApp() {
        actionInteractor.execute(
            Action.ShareAction(stringResolver.getString(R.string.about__play_store_url))
        )
    }

    fun openPrivacyPolicy() {
        actionInteractor.execute(
            Action.OpenUrlAction(stringResolver.getString(R.string.about__privacy_policy_url))
        )
    }

    fun openTermsAndConditions() {
        actionInteractor.execute(
            Action.OpenUrlAction(stringResolver.getString(R.string.about__terms_and_conditions_url))
        )
    }
}