package com.vlad1m1r.bltaxi.about.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.vlad1m1r.basedata.StringResolver
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AboutViewModelShould {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val actionInteractor = mock<ExecuteAction>()
    private val appInfoProvider = mock<AppInfoProvider> {
        on { getVersionName() }.thenReturn("version_name")
    }
    private val stringResolver = mock<StringResolver>()

    private lateinit var aboutViewModel: AboutViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        aboutViewModel = AboutViewModel(actionInteractor, appInfoProvider, stringResolver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialize state with app version`() = runTest {
        val state = aboutViewModel.state.value
        assertThat(state.appVersion).isEqualTo("version_name")
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `send email action`() = runTest {
        val email = "email"
        whenever(stringResolver.getString(R.string.about__email)).thenReturn(email)

        aboutViewModel.sendAction(AboutAction.SendEmailClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(actionInteractor).invoke(Action.SendEmailAction(email))
    }

    @Test
    fun `send rate app action`() = runTest {
        val appId = "app_id"
        whenever(appInfoProvider.getApplicationId()).thenReturn(appId)

        aboutViewModel.sendAction(AboutAction.RateAppClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(actionInteractor).invoke(Action.OpenPlayStoreAction(appId))
    }

    @Test
    fun `send share app action`() = runTest {
        val playStoreUrl = "play_store_url"
        whenever(stringResolver.getString(R.string.about__play_store_url)).thenReturn(playStoreUrl)

        aboutViewModel.sendAction(AboutAction.ShareAppClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(actionInteractor).invoke(Action.ShareAction(playStoreUrl))
    }

    @Test
    fun `send privacy policy action`() = runTest {
        val privacyPolicy = "privacy_policy"
        whenever(stringResolver.getString(R.string.about__privacy_policy_url)).thenReturn(privacyPolicy)

        aboutViewModel.sendAction(AboutAction.PrivacyPolicyClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(actionInteractor).invoke(Action.OpenUrlAction(privacyPolicy))
    }

    @Test
    fun `send terms and conditions action`() = runTest {
        val termsAndConditions = "terms_and_conditions"
        whenever(stringResolver.getString(R.string.about__terms_and_conditions_url)).thenReturn(termsAndConditions)

        aboutViewModel.sendAction(AboutAction.TermsAndConditionsClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(actionInteractor).invoke(Action.OpenUrlAction(termsAndConditions))
    }
}
