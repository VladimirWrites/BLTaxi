package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.vlad1m1r.bltaxi.taxi.domain.Repository
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveTaxiOrderShould {

    private val repository = mock<Repository>()
    private val saveTaxiOrder = SaveTaxiOrder(repository)

    private val itemTaxi = ItemTaxi(
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    @Test
    fun saveOrder() {
        runBlocking {
            val list = listOf(
                itemTaxi.copy(id = 10),
                itemTaxi.copy(id = 20),
                itemTaxi.copy(id = 30)
            )

            saveTaxiOrder(list)

            verify(repository).setItemPosition(10, 0)
            verify(repository).setItemPosition(20, 1)
            verify(repository).setItemPosition(30, 2)
            verifyNoMoreInteractions(repository)
        }
    }
}
