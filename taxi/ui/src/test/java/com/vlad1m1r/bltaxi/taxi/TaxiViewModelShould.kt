package com.vlad1m1r.bltaxi.taxi

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import com.vlad1m1r.bltaxi.taxi.domain.usecase.GetOrderedTaxiList
import com.vlad1m1r.bltaxi.taxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.taxi.domain.usecase.IsViberInstalled
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.taxi.ui.TaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.TaxiAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TaxiViewModelShould {

    private val saveTaxiOrder = mock<SaveTaxiOrder>()
    private val shortcutHandler = mock<ShortcutHandler>()
    private val getOrderedTaxiList = mock<GetOrderedTaxiList>()
    private val executeAction = mock<ExecuteAction>()
    private val isViberInstalled = mock<IsViberInstalled>()
    private val tracker = mock<Tracker>()
    private val dispatchers = CoroutineDispatcherProvider(
        Dispatchers.Unconfined, Dispatchers.Unconfined
    )

    private val taxiViewModel = TaxiViewModel(
        saveTaxiOrder, { shortcutHandler }, getOrderedTaxiList, executeAction, isViberInstalled, tracker, dispatchers
    )

    private val itemTaxi = ItemTaxi(
        id = 10,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )

    private val itemTaxi1 = itemTaxi.copy(id = 1)
    private val itemTaxi2 = itemTaxi.copy(id = 2)

    private fun loadTwoTaxis() = runBlocking {
        whenever(getOrderedTaxiList()).thenReturn(TaxisResult.Success(listOf(itemTaxi1, itemTaxi2)))
        taxiViewModel.sendAction(TaxiAction.LoadTaxis)
    }

    @Test
    fun setTaxiOrder_whenTaxiIsMoved() {
        runBlocking {
            loadTwoTaxis()

            taxiViewModel.sendAction(TaxiAction.MoveTaxi(0, 1))

            verify(saveTaxiOrder).invoke(listOf(itemTaxi2, itemTaxi1))
            assertThat(taxiViewModel.state.value.taxis.map { it.itemTaxi })
                .isEqualTo(listOf(itemTaxi2, itemTaxi1))
        }
    }

    @Test
    fun ignoreMove_whenIndicesAreOutOfBounds() {
        runBlocking {
            loadTwoTaxis()

            taxiViewModel.sendAction(TaxiAction.MoveTaxi(0, 5))

            verifyNoMoreInteractions(saveTaxiOrder)
            assertThat(taxiViewModel.state.value.taxis.map { it.itemTaxi })
                .isEqualTo(listOf(itemTaxi1, itemTaxi2))
        }
    }

    @Test
    @Config(sdk = [25])
    fun createShortcuts_whenSavingOrderIfVersionCode25OrHigher() {
        loadTwoTaxis()

        taxiViewModel.sendAction(TaxiAction.MoveTaxi(0, 1))

        verify(shortcutHandler).addShortcutsForTaxis(listOf(itemTaxi2, itemTaxi1))
    }

    @Test
    @Config(sdk = [24])
    fun doNotCreateShortcuts_whenSavingOrderIfVersionCode24OrLower() {
        loadTwoTaxis()

        taxiViewModel.sendAction(TaxiAction.MoveTaxi(0, 1))

        verifyNoMoreInteractions(shortcutHandler)
    }
}
