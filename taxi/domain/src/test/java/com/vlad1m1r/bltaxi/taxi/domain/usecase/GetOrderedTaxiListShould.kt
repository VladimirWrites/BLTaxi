package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetOrderedTaxiListShould {

    private val getTaxis = mock<GetTaxis>()
    private val orderTaxis = mock<SetOrderOfTaxis>()

    private val getOrderedTaxiList = GetOrderedTaxiList(
        getTaxis,
        orderTaxis
    )

    val itemTaxi = ItemTaxi(
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff(
            start = "2,50 KM",
            pricePerKm = "2,00 KM",
            hourOfWaiting = "25,00 KM/h"
        ),
        tariff2 = Tariff(
            start = "2,50 KM",
            pricePerKm = "2,35 KM",
            hourOfWaiting = "30,00 KM/h"
        ),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )

    @Test
    fun getOrderedList() {
        runBlocking {
            val list = listOf(
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2)
            )

            whenever(getTaxis.invoke()).thenReturn(TaxisResult.Success(list))
            whenever(orderTaxis.invoke(list)).thenReturn(list.asReversed())

            assertThat(getOrderedTaxiList()).isEqualTo(TaxisResult.Success(list.asReversed()))
        }
    }

    @Test
    fun forwardError() {
        runBlocking {
            whenever(getTaxis.invoke()).thenReturn(TaxisResult.Error)

            assertThat(getOrderedTaxiList()).isEqualTo(TaxisResult.Error)
        }
    }
}
