package com.vlad1m1r.bltaxi.local.taxi

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
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
        10,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    private val taxi = Taxi(
        10,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number",
        Language.HR
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
