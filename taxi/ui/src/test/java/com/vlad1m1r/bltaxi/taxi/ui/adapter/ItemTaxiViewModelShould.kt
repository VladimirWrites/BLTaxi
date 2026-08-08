package com.vlad1m1r.bltaxi.taxi.ui.adapter

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import org.junit.Test

class ItemTaxiViewModelShould {
    private val itemTaxi = ItemTaxi(
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )

    @Test
    fun makeViberVisible_whenViberNumberIsNotNullOrBlank() {
        val taxi = itemTaxi.copy(viberNumber = "not_blank")
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, true, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isTrue()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsNull() {
        val taxi = itemTaxi.copy(viberNumber = null)
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, true, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isFalse()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsBlank() {
        val taxi = itemTaxi.copy(viberNumber = "   \t\n")
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, true, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isFalse()
    }

    @Test
    fun executeCallFun_whenCallTaxi() {
        val call = mock<(itemTaxi: ItemTaxi) -> Unit>()
        val itemTaxiViewModel = ItemTaxiViewModel(itemTaxi, true, call, {})

        itemTaxiViewModel.callTaxi()

        verify(call).invoke(itemTaxi)
    }

    @Test
    fun executeCallViberFun_whenCallTaxiOnViber() {
        val callViber = mock<(itemTaxi: ItemTaxi) -> Unit>()
        val itemTaxiViewModel = ItemTaxiViewModel(itemTaxi, true, {}, callViber)

        itemTaxiViewModel.callTaxiOnViber()

        verify(callViber).invoke(itemTaxi)
    }
}
