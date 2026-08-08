package com.vlad1m1r.bltaxi.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TaxiShould {

    val taxi = Taxi(
        10,
        "name",
        "number",
        Tariff("2,50 KM", "2,00 KM", "25,00 KM/h"),
        Tariff("2,50 KM", "2,35 KM", "30,00 KM/h"),
        "additional",
        "viber"
    )

    @Test
    fun mapToItemTaxi() {
        val itemTaxi = taxi.toItemTaxi()

        assertThat(itemTaxi.id).isEqualTo(taxi.id)
        assertThat(itemTaxi.name).isEqualTo(taxi.name)
        assertThat(itemTaxi.phoneNumber).isEqualTo(taxi.number)
        assertThat(itemTaxi.tariff1.start).isEqualTo(taxi.tariff1.start)
        assertThat(itemTaxi.tariff1.pricePerKm).isEqualTo(taxi.tariff1.pricePerKm)
        assertThat(itemTaxi.tariff1.hourOfWaiting).isEqualTo(taxi.tariff1.hourOfWaiting)
        assertThat(itemTaxi.tariff2.start).isEqualTo(taxi.tariff2.start)
        assertThat(itemTaxi.tariff2.pricePerKm).isEqualTo(taxi.tariff2.pricePerKm)
        assertThat(itemTaxi.tariff2.hourOfWaiting).isEqualTo(taxi.tariff2.hourOfWaiting)
        assertThat(itemTaxi.additionalInfo).isEqualTo(taxi.additional)
        assertThat(itemTaxi.viberNumber).isEqualTo(taxi.viber)
    }
}
