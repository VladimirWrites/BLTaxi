package com.vlad1m1r.bltaxi.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TaxiShould {

    val taxi = Taxi(
        10,
        "name",
        "number",
        "start",
        "price",
        "additional",
        "viber"
    )

    @Test
    fun mapToItemTaxi() {
        val itemTaxi = taxi.toItemTaxi()

        assertThat(itemTaxi.id).isEqualTo(taxi.id)
        assertThat(itemTaxi.name).isEqualTo(taxi.name)
        assertThat(itemTaxi.phoneNumber).isEqualTo(taxi.number)
        assertThat(itemTaxi.startPrice).isEqualTo(taxi.start)
        assertThat(itemTaxi.pricePerKm).isEqualTo(taxi.price)
        assertThat(itemTaxi.additionalInfo).isEqualTo(taxi.additional)
        assertThat(itemTaxi.viberNumber).isEqualTo(taxi.viber)
    }
}
