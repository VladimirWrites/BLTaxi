package com.vlad1m1r.bltaxi.taxi.domain.usecase

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveTaxiOrderShould {

    private val repository = mock<TaxiRepository>()
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
