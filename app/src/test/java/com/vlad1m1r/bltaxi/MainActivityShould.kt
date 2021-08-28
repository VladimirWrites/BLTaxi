package com.vlad1m1r.bltaxi

import android.os.Build
import androidx.lifecycle.Lifecycle.State
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.di.AppModule
import com.vlad1m1r.bltaxi.taxi.ui.TaxiNavigator
import dagger.hilt.android.testing.*
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@UninstallModules(AppModule::class)
@HiltAndroidTest
@Config(sdk = [Build.VERSION_CODES.P], application = HiltTestApplication::class)
class MainActivityShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val navigator: TaxiNavigator = mock<Navigator>()

    @BindValue
    val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined
    )

    @Test
    fun bindNavigator_onResume() {
        val scenario = launchActivity<MainActivity>()

        scenario.moveToState(State.RESUMED)

        scenario.onActivity {
            verify(navigator as Navigator).bind(it.findNavController(R.id.nav_host_fragment))
        }
    }

    @Test
    fun unbindNavigator_onPause() {
        val scenario = launchActivity<MainActivity>()

        scenario.moveToState(State.STARTED)

        verify(navigator as Navigator).unbind()
    }

    @Test
    fun navigateUp() {
        val scenario = launchActivity<MainActivity>()

        scenario.onActivity {
            it.onSupportNavigateUp()
        }

        verify(navigator as Navigator).navigateUp()
    }
}