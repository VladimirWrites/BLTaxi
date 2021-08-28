package com.vlad1m1r.bltaxi.taxi

import android.os.Build
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.usecase.GetOrderedTaxiList
import com.vlad1m1r.bltaxi.taxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.taxi.ui.TaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class TaxiViewModelShould {

    private val saveTaxiOrder = mock<SaveTaxiOrder>()
    private val shortcutHandler = mock<ShortcutHandler>()
    private val getOrderedTaxiList = mock<GetOrderedTaxiList>()
    private val executeAction = mock<ExecuteAction>()
    private val tracker = mock<Tracker>()
    private val dispatchers = CoroutineDispatcherProvider(
        Dispatchers.Unconfined, Dispatchers.Unconfined
    )

    private val taxiViewModel = TaxiViewModel(
        saveTaxiOrder, { shortcutHandler }, getOrderedTaxiList, executeAction, tracker, dispatchers
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
                ItemTaxiViewModel(itemTaxi1, {}, {}),
                ItemTaxiViewModel(itemTaxi2, {}, {})
            )
            taxiViewModel.setTaxiOrder(listItemTaxiViewModel)

            verify(saveTaxiOrder).invoke(listOf(itemTaxi1, itemTaxi2))
        }
    }

    @Test
    fun createShortcuts_whenSavingOrderIfVersionCode25OrHigher() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 25)

        val itemTaxi1 = itemTaxi.copy(id = 1)
        val itemTaxi2 = itemTaxi.copy(id = 2)
        val listItemTaxiViewModel = listOf(
            ItemTaxiViewModel(itemTaxi1, {}, {}),
            ItemTaxiViewModel(itemTaxi2, {}, {})
        )

        taxiViewModel.setTaxiOrder(listItemTaxiViewModel)

        verify(shortcutHandler).addShortcutsForTaxis(listOf(itemTaxi1, itemTaxi2))

    }

    @Test
    fun doNotCreateShortcuts_whenSavingOrderIfVersionCode24OrLower() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 24)

        val itemTaxi1 = itemTaxi.copy(id = 1)
        val itemTaxi2 = itemTaxi.copy(id = 2)
        val listItemTaxiViewModel = listOf(
            ItemTaxiViewModel(itemTaxi1, {}, {}),
            ItemTaxiViewModel(itemTaxi2, {}, {})
        )

        taxiViewModel.setTaxiOrder(listItemTaxiViewModel)

        verifyNoMoreInteractions(shortcutHandler)
    }

    private fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }
}