package com.vlad1m1r.bltaxi.about.ui

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import org.junit.Test

class AboutViewModelShould {

    private val actionInteractor = mock<ExecuteAction>()
    private val appInfoProvider = mock<AppInfoProvider> {
        on { getVersionName() }.thenReturn("version_name")
    }
    private val stringResolver = mock<StringResolver>()

    private val aboutViewModel = AboutViewModel(actionInteractor, appInfoProvider, stringResolver)

    @Test
    fun returnVersionName() {
        assertThat(aboutViewModel.getAppVersionName()).isEqualTo("version_name")
    }

    @Test
    fun writeEmail() {
        val email = "email"
        whenever(stringResolver.getString(R.string.about__email)).thenReturn(email)
        aboutViewModel.writeEmail()
        verify(actionInteractor).invoke(Action.SendEmailAction(email))
    }

    @Test
    fun rateApp() {
        val appId = "app_id"
        whenever(appInfoProvider.getApplicationId()).thenReturn(appId)
        aboutViewModel.rateApp()
        verify(actionInteractor).invoke(Action.OpenPlayStoreAction(appId))
    }

    @Test
    fun shareApp() {
        val playStoreUrl = "play_store_url"
        whenever(stringResolver.getString(R.string.about__play_store_url)).thenReturn(playStoreUrl)
        aboutViewModel.shareApp()
        verify(actionInteractor).invoke(Action.ShareAction(playStoreUrl))
    }

    @Test
    fun openPrivacyPolicy() {
        val privacyPolicy = "privacy_policy"
        whenever(stringResolver.getString(R.string.about__privacy_policy_url)).thenReturn(privacyPolicy)
        aboutViewModel.openPrivacyPolicy()
        verify(actionInteractor).invoke(Action.OpenUrlAction(privacyPolicy))
    }

    @Test
    fun openTermsAndConditions() {
        val termsAndConditions = "terms_and_conditions"
        whenever(stringResolver.getString(R.string.about__terms_and_conditions_url)).thenReturn(termsAndConditions)
        aboutViewModel.openTermsAndConditions()
        verify(actionInteractor).invoke(Action.OpenUrlAction(termsAndConditions))
    }
}
