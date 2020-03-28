package com.vlad1m1r.bltaxi

import android.app.Application
import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.bltaxi.taxi.TaxiNavigator
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import com.vlad1m1r.bltaxi.taxi.adapter.ItemTaxiViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
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
            modules(emptyList())
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApplication::class)
class MainActivityShould {
    val taxis: LiveData<List<ItemTaxiViewModel>> = MutableLiveData()
    val isLoading = ObservableBoolean(false)
    val isErrorShown = ObservableBoolean(false)

    val navigator = mock<Navigator>()
    val taxiViewModel = mock<TaxiViewModel> {
        on { taxis }.thenReturn(taxis)
        on { isLoading }.thenReturn(isLoading)
        on { isErrorShown }.thenReturn(isErrorShown)
    }

    @Before
    fun before() {
        val applicationContext =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        applicationContext.injectModule(
            module {
                single<TaxiNavigator> { navigator }
                single { taxiViewModel }
            }
        )
    }

    @Test
    fun bindNavigator_onResume() {
        val scenario = launchActivity<MainActivity>()

        scenario.moveToState(State.RESUMED)

        scenario.onActivity {
            verify(navigator).bind(it.findNavController(R.id.nav_host_fragment))
        }
    }

    @Test
    fun unbindNavigator_onPause() {
        val scenario = launchActivity<MainActivity>()

        scenario.moveToState(State.STARTED)

        verify(navigator).unbind()
    }

    @Test
    fun navigateUp() {
        val scenario = launchActivity<MainActivity>()

        scenario.onActivity {
            it.onSupportNavigateUp()
        }

        verify(navigator).navigateUp()
    }
}