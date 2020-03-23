package com.vlad1m1r.bltaxi.local.taxi

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.local.database.Taxi
import com.vlad1m1r.bltaxi.local.database.TaxiDao
import com.vlad1m1r.bltaxi.local.database.TaxiDatabase
import com.vlad1m1r.bltaxi.local.database.toItemTaxi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TaxiProviderLocalShould {
    val taxiDao = mock<TaxiDao>()
    val taxiDatabase = mock<TaxiDatabase>() {
        on { taxiDao() }.thenReturn(taxiDao)
    }
    val taxiProviderLocal: TaxiProviderLocal = TaxiProviderLocalImpl(taxiDatabase)

    val itemTaxi = ItemTaxi(
        10,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    val taxi = Taxi(
        10,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number",
        100L,
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

            verify(taxiDao).replaceAll(taxis, Language.EN)
        }
    }
}