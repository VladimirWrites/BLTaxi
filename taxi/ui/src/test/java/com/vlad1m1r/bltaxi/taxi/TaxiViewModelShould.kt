package com.vlad1m1r.bltaxi.taxi

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.usecase.GetOrderedTaxiList
import com.vlad1m1r.bltaxi.taxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.taxi.domain.usecase.IsViberInstalled
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.taxi.ui.TaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.TaxiAction
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
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
        10,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    @Test
    fun setTaxiOrder() {
        runBlocking {
            val itemTaxi1 = itemTaxi.copy(id = 1)
            val itemTaxi2 = itemTaxi.copy(id = 2)
            val listItemTaxiViewModel = listOf(
                ItemTaxiViewModel(itemTaxi1, false, {}, {}),
                ItemTaxiViewModel(itemTaxi2, false, {}, {})
            )
            taxiViewModel.sendAction(TaxiAction.ReorderTaxis(listItemTaxiViewModel))

            verify(saveTaxiOrder).invoke(listOf(itemTaxi1, itemTaxi2))
        }
    }

    @Test
    @Config(sdk = [25])
    fun createShortcuts_whenSavingOrderIfVersionCode25OrHigher() {
        val itemTaxi1 = itemTaxi.copy(id = 1)
        val itemTaxi2 = itemTaxi.copy(id = 2)
        val listItemTaxiViewModel = listOf(
            ItemTaxiViewModel(itemTaxi1, false, {}, {}),
            ItemTaxiViewModel(itemTaxi2, false, {}, {})
        )

        taxiViewModel.sendAction(TaxiAction.ReorderTaxis(listItemTaxiViewModel))

        verify(shortcutHandler).addShortcutsForTaxis(listOf(itemTaxi1, itemTaxi2))
    }

    @Test
    @Config(sdk = [24])
    fun doNotCreateShortcuts_whenSavingOrderIfVersionCode24OrLower() {
        val itemTaxi1 = itemTaxi.copy(id = 1)
        val itemTaxi2 = itemTaxi.copy(id = 2)
        val listItemTaxiViewModel = listOf(
            ItemTaxiViewModel(itemTaxi1, false, {}, {}),
            ItemTaxiViewModel(itemTaxi2, false, {}, {})
        )

        taxiViewModel.sendAction(TaxiAction.ReorderTaxis(listItemTaxiViewModel))

        verifyNoMoreInteractions(shortcutHandler)
    }
}