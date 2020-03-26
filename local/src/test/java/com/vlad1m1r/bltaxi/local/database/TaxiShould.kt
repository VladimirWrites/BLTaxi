package com.vlad1m1r.bltaxi.local.database

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import org.junit.Test

class TaxiShould {

    @Test
    fun toItemTaxi() {
        val taxi = Taxi(
            10,
            "name",
            "phone_number",
            "start_price",
            "price_per_km",
            "additional_info",
            "viber_number",
            Language.HR
        )

        val itemTaxi = taxi.toItemTaxi()

        assertThat(itemTaxi.id).isEqualTo(10)
        assertThat(itemTaxi.name).isEqualTo("name")
        assertThat(itemTaxi.phoneNumber).isEqualTo("phone_number")
        assertThat(itemTaxi.startPrice).isEqualTo("start_price")
        assertThat(itemTaxi.pricePerKm).isEqualTo("price_per_km")
        assertThat(itemTaxi.additionalInfo).isEqualTo("additional_info")
        assertThat(itemTaxi.viberNumber).isEqualTo("viber_number")

    }

    @Test
    fun toTaxi() {
        val itemTaxi = ItemTaxi(
            10,
            "name",
            "phone_number",
            "start_price",
            "price_per_km",
            "additional_info",
            "viber_number"
        )

        val taxi = itemTaxi.toTaxi(Language.BS)

        assertThat(taxi.taxiId).isEqualTo(10)
        assertThat(taxi.name).isEqualTo("name")
        assertThat(taxi.phoneNumber).isEqualTo("phone_number")
        assertThat(taxi.startPrice).isEqualTo("start_price")
        assertThat(taxi.pricePerKm).isEqualTo("price_per_km")
        assertThat(taxi.additionalInfo).isEqualTo("additional_info")
        assertThat(taxi.viberNumber).isEqualTo("viber_number")
        assertThat(taxi.language).isEqualTo(Language.BS)
    }
}