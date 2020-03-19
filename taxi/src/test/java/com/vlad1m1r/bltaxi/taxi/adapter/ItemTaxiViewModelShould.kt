package com.vlad1m1r.bltaxi.taxi.adapter

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import org.junit.Test

class ItemTaxiViewModelShould {
    val itemTaxi = ItemTaxi(
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    @Test
    fun makeViberVisible_whenViberNumberIsNotNullOrBlank() {
        val taxi = itemTaxi.copy(viberNumber = "not_blank")
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isTrue()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsNull() {
        val taxi = itemTaxi.copy(viberNumber = null)
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isFalse()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsBlank() {
        val taxi = itemTaxi.copy(viberNumber = "   \t\n")
        val itemTaxiViewModel = ItemTaxiViewModel(taxi, {}, {})

        assertThat(itemTaxiViewModel.isViberVisible).isFalse()
    }
}
