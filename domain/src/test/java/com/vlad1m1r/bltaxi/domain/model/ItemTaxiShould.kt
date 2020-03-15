package com.vlad1m1r.bltaxi.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ItemTaxiShould {

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

        assertThat(taxi.isViberVisible).isTrue()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsNull() {
        val taxi = itemTaxi.copy(viberNumber = null)

        assertThat(taxi.isViberVisible).isFalse()
    }

    @Test
    fun makeViberNotVisible_whenViberNumberIsBlank() {
        val taxi = itemTaxi.copy(viberNumber = "   \t\n")

        assertThat(taxi.isViberVisible).isFalse()
    }
}
