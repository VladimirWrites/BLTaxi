package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.bltaxi.taxi.domain.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTaxiPositionShould {

    private val repository = mock<Repository>()
    private val getTaxiPosition = GetTaxiPosition(repository)

    @Test
    fun getPosition() {
        runBlocking {
            whenever(repository.getItemPosition(10)).thenReturn(1)
            whenever(repository.getItemPosition(11)).thenReturn(2)
            whenever(repository.getItemPosition(12)).thenReturn(3)

            assertThat(getTaxiPosition(10)).isEqualTo(1)
            assertThat(getTaxiPosition(11)).isEqualTo(2)
            assertThat(getTaxiPosition(12)).isEqualTo(3)

        }
    }
}
