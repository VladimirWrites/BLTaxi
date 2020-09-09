package com.vlad1m1r.bltaxi.taxi

import android.app.Application
import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
class TaxiFragmentShould {

    val taxis: LiveData<List<ItemTaxiViewModel>> = MutableLiveData<List<ItemTaxiViewModel>>()
    val isLoading = ObservableBoolean(false)
    val isErrorShown = ObservableBoolean(false)

    val fragmentViewModel = mock<TaxiViewModel> {
        on { taxis }.thenReturn(taxis)
        on { isLoading }.thenReturn(isLoading)
        on { isErrorShown }.thenReturn(isErrorShown)
    }
    
    val navigator = mock<TaxiNavigator>()

    @Before
    fun before() {
        val applicationContext =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        applicationContext.injectModule(
            module {
                single {
                    fragmentViewModel
                }
                single {
                    navigator
                }
            }
        )
    }

    @Test
    fun setTaxiOrder_whenSaveChangesIsCalled() {
        val list = listOf<ItemTaxiViewModel>()

        launchFragmentInContainer<TaxiFragment>().onFragment {
            it.saveChanges(list)
        }

        verify(fragmentViewModel).setTaxiOrder(list)
    }
}