package com.vlad1m1r.bltaxi.taxi

import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.bltaxi.launchFragmentInHiltContainer
import com.vlad1m1r.bltaxi.taxi.ui.TaxiFragment
import com.vlad1m1r.bltaxi.taxi.ui.TaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
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
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = HiltTestApplication::class,
    instrumentedPackages = ["androidx.loader.content"]
)
class TaxiFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val taxis: LiveData<List<ItemTaxiViewModel>> = MutableLiveData()
    private val isLoading = ObservableBoolean(false)
    private val isErrorShown = ObservableBoolean(false)

    private val viewModel = mock<TaxiViewModel> {
        on { taxis }.thenReturn(taxis)
        on { isLoading }.thenReturn(isLoading)
        on { isErrorShown }.thenReturn(isErrorShown)
    }

    @Test
    fun setTaxiOrder_whenSaveChangesIsCalled() {
        val list = listOf<ItemTaxiViewModel>()

        launchTaxiFragmentWithViewModel {
            it.saveChanges(list)
        }

        verify(viewModel).setTaxiOrder(list)
    }

    private fun launchTaxiFragmentWithViewModel(onFragment: (TaxiFragment) -> Unit) {
        launchFragmentInHiltContainer<TaxiFragment> {
            (this as TaxiFragment).setViewModel(viewModel)
            onFragment(this)
        }
    }
}