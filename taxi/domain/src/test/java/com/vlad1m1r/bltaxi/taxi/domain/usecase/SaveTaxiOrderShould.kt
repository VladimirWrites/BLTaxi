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
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = com.vlad1m1r.bltaxi.taxi.domain.model.Tariff(
            start = "2,50 KM",
            pricePerKm = "2,00 KM",
            hourOfWaiting = "25,00 KM/h"
        ),
        tariff2 = com.vlad1m1r.bltaxi.taxi.domain.model.Tariff(
            start = "2,50 KM",
            pricePerKm = "2,35 KM",
            hourOfWaiting = "30,00 KM/h"
        ),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
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
