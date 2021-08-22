package com.vlad1m1r.bltaxi.about

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.test.core.app.ActivityScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(sdk = [Build.VERSION_CODES.P], application = HiltTestApplication::class)
class AboutFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val fragmentViewModel = mock<AboutViewModel>()

    @Test
    fun sendEmail_whenButtonSendEmailIsClicked() {
        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { clickButtonSendEmail() }

        verify(fragmentViewModel).writeEmail()
    }

    @Test
    fun rateApp_whenButtonRateAppIsClicked() {
        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { clickButtonRateApp() }

        verify(fragmentViewModel).rateApp()
    }

    @Test
    fun shareApp_whenButtonShareAppIsClicked() {
        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { clickButtonShareApp() }

        verify(fragmentViewModel).shareApp()
    }

    @Test
    fun openPrivacyPolicy_whenButtonPrivacyPolicyIsClicked() {
        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { clickButtonPrivacyPolicy() }

        verify(fragmentViewModel).openPrivacyPolicy()
    }

    @Test
    fun openTermsAndConditions_whenButtonTermsAndConditionsIsClicked() {
        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { clickButtonTermsAndConditions() }

        verify(fragmentViewModel).openTermsAndConditions()
    }

    @Test
    fun showVersionName() {
        whenever(fragmentViewModel.getAppVersionName()).thenReturn("version_name")

        launchFragmentInHiltContainer<AboutFragment>()

        aboutScreen { textAppVersionIsEqualTo("Version: version_name") }
    }
}