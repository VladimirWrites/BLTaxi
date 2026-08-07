package com.vlad1m1r.bltaxi.local.taxi

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import com.vlad1m1r.bltaxi.local.database.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TaxiProviderLocalShould {
    private val taxiDao = mock<TaxiDao>()
    private val taxiDatabase = mock<TaxiDatabase> {
        on { taxiDao() }.thenReturn(taxiDao)
    }
    private val taxiProviderLocal: TaxiProviderLocal = TaxiProviderLocalImpl(taxiDatabase)

    private val itemTaxi = ItemTaxi(
        id = 10,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )

    private val taxi = Taxi(
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

    @Test
    fun returnEmptyList_whenDatabaseReturnsNull() {
        runBlocking {
            whenever(taxiDao.getAll(any())).thenReturn(null)

            val result = taxiProviderLocal.getTaxis(Language.EN)

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun returnEmptyList_whenDatabaseReturnsEmptyList() {
        runBlocking {
            whenever(taxiDao.getAll(any())).thenReturn(emptyList())

            val result = taxiProviderLocal.getTaxis(Language.EN)

            assertThat(result).isEmpty()
        }
    }

    @Test
    fun mapList_providedByDatabase() {
        runBlocking {
            whenever(taxiDao.getAll(any())).thenReturn(listOf(taxi))

            val result = taxiProviderLocal.getTaxis(Language.EN)

            assertThat(result).containsExactly(taxi.toItemTaxi())
        }
    }

    @Test
    fun saveTaxis() {
        runBlocking {
            val taxis = listOf(itemTaxi)

            taxiProviderLocal.saveTaxis(taxis, Language.EN)

            verify(taxiDao).replaceAll(taxis.map { it.toTaxi(Language.EN) }, Language.EN)
        }
    }
}
