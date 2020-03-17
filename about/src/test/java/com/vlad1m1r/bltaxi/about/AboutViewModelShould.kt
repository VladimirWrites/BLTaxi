package com.vlad1m1r.bltaxi.about

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.di.AppInfoProvider
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.interactor.ActionInteractor
import org.junit.Test

class AboutViewModelShould {

    private val actionInteractor = mock<ActionInteractor>()
    private val appInfoProvider = mock<AppInfoProvider>() {
        on { getVersionName() }.thenReturn("verson_name")
    }
    private val stringResolver = mock<StringResolver>()

    val aboutViewModel = AboutViewModel(actionInteractor, appInfoProvider, stringResolver)

    @Test
    fun returnVersionName() {
        assertThat(aboutViewModel.appVersionName).isEqualTo("verson_name")
    }

    @Test
    fun writeEmail() {
        val email = "email"
        whenever(stringResolver.getString(R.string.about__email)).thenReturn(email)
        aboutViewModel.writeEmail()
        verify(actionInteractor).execute(Action.SendEmailAction(email))
    }

    @Test
    fun rateApp() {
        val appId = "app_id"
        whenever(appInfoProvider.getApplicationId()).thenReturn(appId)
        aboutViewModel.rateApp()
        verify(actionInteractor).execute(Action.OpenPlayStoreAction(appId))
    }

    @Test
    fun shareApp() {
        val playStoreUrl = "play_store_url"
        whenever(stringResolver.getString(R.string.about__play_store_url)).thenReturn(playStoreUrl)
        aboutViewModel.shareApp()
        verify(actionInteractor).execute(Action.ShareAction(playStoreUrl))
    }

    @Test
    fun openPrivacyPolicy() {
        val privacyPolicy = "privacy_policy"
        whenever(stringResolver.getString(R.string.about__privacy_policy_url)).thenReturn(privacyPolicy)
        aboutViewModel.openPrivacyPolicy()
        verify(actionInteractor).execute(Action.OpenUrlAction(privacyPolicy))
    }

    @Test
    fun openTermsAndConditions() {
        val termsAndConditions = "terms_and_conditions"
        whenever(stringResolver.getString(R.string.about__terms_and_conditions_url)).thenReturn(termsAndConditions)
        aboutViewModel.openTermsAndConditions()
        verify(actionInteractor).execute(Action.OpenUrlAction(termsAndConditions))
    }
}
