package com.vlad1m1r.bltaxi.about

import android.app.Application
import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_App)
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            // TODO Await fix for Koin and replace the explicit invocations
            //  of loadModules() and createRootScope() with a single call to modules()
            //  (https://github.com/InsertKoinIO/koin/issues/847)
            koin.loadModules(emptyList())
            koin.createRootScope()
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApplication::class)
class AboutFragmentShould {

    val fragmentViewModel = mock<AboutViewModel>()

    @Before
    fun before() {
        val applicationContext =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        applicationContext.injectModule(
            module {
                single {
                    fragmentViewModel
                }
            }
        )
    }

    @Test
    fun sendEmail_whenButtonSendEmailIsClicked() {
        launchFragmentInContainer<AboutFragment>()

        aboutScreen { clickButtonSendEmail() }

        verify(fragmentViewModel).writeEmail()
    }

    @Test
    fun rateApp_whenButtonRateAppIsClicked() {
        launchFragmentInContainer<AboutFragment>()

        aboutScreen { clickButtonRateApp() }

        verify(fragmentViewModel).rateApp()
    }

    @Test
    fun shareApp_whenButtonShareAppIsClicked() {
        launchFragmentInContainer<AboutFragment>()

        aboutScreen { clickButtonShareApp() }

        verify(fragmentViewModel).shareApp()
    }

    @Test
    fun openPrivacyPolicy_whenButtonPrivacyPolicyIsClicked() {
        launchFragmentInContainer<AboutFragment>()

        aboutScreen { clickButtonPrivacyPolicy() }

        verify(fragmentViewModel).openPrivacyPolicy()
    }

    @Test
    fun openTermsAndConditions_whenButtonTermsAndConditionsIsClicked() {
        launchFragmentInContainer<AboutFragment>()

        aboutScreen { clickButtonTermsAndConditions() }

        verify(fragmentViewModel).openTermsAndConditions()
    }

    @Test
    fun showVersionName() {
        whenever(fragmentViewModel.getAppVersionName()).thenReturn("version_name")

        launchFragmentInContainer<AboutFragment>()

        aboutScreen { textAppVersionIsEqualTo("Version: version_name") }
    }
}