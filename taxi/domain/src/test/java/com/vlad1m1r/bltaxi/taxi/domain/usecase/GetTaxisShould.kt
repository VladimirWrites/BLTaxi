package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.bltaxi.taxi.domain.Repository
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTaxisShould {

    private val repository = mock<Repository>()
    private val getTaxis = GetTaxis(repository)

    @Test
    fun getTaxisFromRepository() {
        runBlocking {
            val taxiResult = TaxisResult.Success(listOf())
            whenever(repository.getTaxis()).thenReturn(taxiResult)

            assertThat(getTaxis()).isSameInstanceAs(taxiResult)
        }
    }
}
