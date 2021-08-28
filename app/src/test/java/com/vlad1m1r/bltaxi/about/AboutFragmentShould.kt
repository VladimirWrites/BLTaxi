package com.vlad1m1r.bltaxi.about

import android.os.Build
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.about.ui.AboutFragment
import com.vlad1m1r.bltaxi.about.ui.AboutViewModel
import com.vlad1m1r.bltaxi.launchFragmentInHiltContainer
import dagger.hilt.android.testing.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = HiltTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
class AboutFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val viewModel = mock<AboutViewModel>()

    @Test
    fun sendEmail_whenButtonSendEmailIsClicked() {
        launchAboutFragmentWithViewModel()

        aboutScreen { clickButtonSendEmail() }

        verify(viewModel).writeEmail()
    }

    @Test
    fun rateApp_whenButtonRateAppIsClicked() {
        launchAboutFragmentWithViewModel()

        aboutScreen { clickButtonRateApp() }

        verify(viewModel).rateApp()
    }

    @Test
    fun shareApp_whenButtonShareAppIsClicked() {
        launchAboutFragmentWithViewModel()

        aboutScreen { clickButtonShareApp() }

        verify(viewModel).shareApp()
    }

    @Test
    fun openPrivacyPolicy_whenButtonPrivacyPolicyIsClicked() {
        launchAboutFragmentWithViewModel()

        aboutScreen { clickButtonPrivacyPolicy() }

        verify(viewModel).openPrivacyPolicy()
    }

    @Test
    fun openTermsAndConditions_whenButtonTermsAndConditionsIsClicked() {
        launchAboutFragmentWithViewModel()

        aboutScreen { clickButtonTermsAndConditions() }

        verify(viewModel).openTermsAndConditions()
    }

    @Test
    fun showVersionName() {
        whenever(viewModel.getAppVersionName()).thenReturn("version_name")

        launchAboutFragmentWithViewModel()

        aboutScreen { textAppVersionIsEqualTo("Version: version_name") }
    }

    private fun launchAboutFragmentWithViewModel() {
        launchFragmentInHiltContainer<AboutFragment> {
            (this as AboutFragment).setViewModel(viewModel)
        }
    }
}