package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveTaxiPositionShould {

    private val repository = mock<TaxiRepository>()
    private val saveTaxiPosition = SaveTaxiPosition(repository)

    @Test
    fun getPosition() {
        runBlocking {
            saveTaxiPosition(10, 1)
            saveTaxiPosition(11, 2)
            saveTaxiPosition(12, 3)

            verify(repository).setItemPosition(10, 1)
            verify(repository).setItemPosition(11, 2)
            verify(repository).setItemPosition(12, 3)
        }
    }
}
