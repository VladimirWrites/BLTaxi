package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
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
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
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
