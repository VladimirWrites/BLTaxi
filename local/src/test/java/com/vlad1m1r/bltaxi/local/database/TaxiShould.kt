package com.vlad1m1r.bltaxi.local.database

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import org.junit.Test

class TaxiShould {

    private val tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting")
    private val tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2")

    @Test
    fun toItemTaxi() {
        val taxi = Taxi(
            taxiId = 10,
            name = "name",
            phoneNumber = "phone_number",
            tariff1Start = "start_price",
            tariff1PricePerKm = "price_per_km",
            tariff1HourOfWaiting = "hour_of_waiting",
            tariff2Start = "start_price_2",
            tariff2PricePerKm = "price_per_km_2",
            tariff2HourOfWaiting = "hour_of_waiting_2",
            additionalInfo = "additional_info",
            viberNumber = "viber_number",
            language = Language.HR
        )

        val itemTaxi = taxi.toItemTaxi()

        assertThat(itemTaxi.id).isEqualTo(10)
        assertThat(itemTaxi.name).isEqualTo("name")
        assertThat(itemTaxi.phoneNumber).isEqualTo("phone_number")
        assertThat(itemTaxi.tariff1).isEqualTo(tariff1)
        assertThat(itemTaxi.tariff2).isEqualTo(tariff2)
        assertThat(itemTaxi.additionalInfo).isEqualTo("additional_info")
        assertThat(itemTaxi.viberNumber).isEqualTo("viber_number")
    }

    @Test
    fun toTaxi() {
        val itemTaxi = ItemTaxi(
            id = 10,
            name = "name",
            phoneNumber = "phone_number",
            tariff1 = tariff1,
            tariff2 = tariff2,
            additionalInfo = "additional_info",
            viberNumber = "viber_number"
        )

        val taxi = itemTaxi.toTaxi(Language.BS)

        assertThat(taxi.taxiId).isEqualTo(10)
        assertThat(taxi.name).isEqualTo("name")
        assertThat(taxi.phoneNumber).isEqualTo("phone_number")
        assertThat(taxi.tariff1Start).isEqualTo("start_price")
        assertThat(taxi.tariff1PricePerKm).isEqualTo("price_per_km")
        assertThat(taxi.tariff1HourOfWaiting).isEqualTo("hour_of_waiting")
        assertThat(taxi.tariff2Start).isEqualTo("start_price_2")
        assertThat(taxi.tariff2PricePerKm).isEqualTo("price_per_km_2")
        assertThat(taxi.tariff2HourOfWaiting).isEqualTo("hour_of_waiting_2")
        assertThat(taxi.additionalInfo).isEqualTo("additional_info")
        assertThat(taxi.viberNumber).isEqualTo("viber_number")
        assertThat(taxi.language).isEqualTo(Language.BS)
    }
}
