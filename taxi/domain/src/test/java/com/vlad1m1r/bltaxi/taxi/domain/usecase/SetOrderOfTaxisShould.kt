package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import kotlinx.coroutines.runBlocking
import org.junit.Test


class SetOrderOfTaxisShould {

    private val getTaxiPosition = mock<GetTaxiPosition>()
    private val saveTaxiOrder = mock<SaveTaxiOrder>()

    private val orderTaxis = SetOrderOfTaxis(
        getTaxiPosition,
        saveTaxiOrder
    )

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
    fun orderListOfTaxis() {
        runBlocking {
            val listOfTaxis = listOf(
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2)
            )
            whenever(getTaxiPosition.invoke(0)).thenReturn(2)
            whenever(getTaxiPosition.invoke(1)).thenReturn(0)
            whenever(getTaxiPosition.invoke(2)).thenReturn(1)

            val actual = orderTaxis(listOfTaxis)

            val expected = listOf(
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2),
                itemTaxi.copy(id = 0)
            )

            verifyNoMoreInteractions(saveTaxiOrder)
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun positionItemsOutOfRangeAtEnd() {
        runBlocking {
            val listOfTaxis = listOf(
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2)
            )

            whenever(getTaxiPosition.invoke(0)).thenReturn(4)
            whenever(getTaxiPosition.invoke(1)).thenReturn(0)
            whenever(getTaxiPosition.invoke(2)).thenReturn(1)

            val actual = orderTaxis(listOfTaxis)

            val expected = listOf(
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2),
                itemTaxi.copy(id = 0)
            )

            verify(saveTaxiOrder).invoke(expected)
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun useServerOrder_ifOrderNotSaved() {
        runBlocking {
            val listOfTaxis = listOf(
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2)
            )

            whenever(getTaxiPosition.invoke(0)).thenReturn(-1)
            whenever(getTaxiPosition.invoke(1)).thenReturn(-1)
            whenever(getTaxiPosition.invoke(2)).thenReturn(-1)

            val orderedList = orderTaxis(listOfTaxis)

            verify(saveTaxiOrder).invoke(orderedList)
            assertThat(orderedList).isEqualTo(listOfTaxis)
        }
    }

    @Test
    fun removeGap_ifItemIsRemoved() {
        runBlocking {
            val listOfTaxis = listOf(
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 2)
            )

            whenever(getTaxiPosition.invoke(0)).thenReturn(2)
            whenever(getTaxiPosition.invoke(1)).thenReturn(1)
            whenever(getTaxiPosition.invoke(2)).thenReturn(0)

            val actual = orderTaxis(listOfTaxis)

            val expected = listOf(
                itemTaxi.copy(id = 2),
                itemTaxi.copy(id = 0)
            )

            verify(saveTaxiOrder).invoke(actual)
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun putNewItemAtEnd() {
        runBlocking {
            val listOfTaxis = listOf(
                itemTaxi.copy(id = 3),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 2),
                itemTaxi.copy(id = 0)
            )

            whenever(getTaxiPosition.invoke(0)).thenReturn(2)
            whenever(getTaxiPosition.invoke(1)).thenReturn(1)
            whenever(getTaxiPosition.invoke(2)).thenReturn(0)
            whenever(getTaxiPosition.invoke(3)).thenReturn(-1)

            val actual = orderTaxis(listOfTaxis)

            val expected = listOf(
                itemTaxi.copy(id = 2),
                itemTaxi.copy(id = 1),
                itemTaxi.copy(id = 0),
                itemTaxi.copy(id = 3)
            )

            verify(saveTaxiOrder).invoke(actual)
            assertThat(actual).isEqualTo(expected)
        }
    }
}
