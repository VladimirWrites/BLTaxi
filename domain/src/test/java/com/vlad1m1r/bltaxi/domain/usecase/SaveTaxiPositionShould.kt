package com.vlad1m1r.bltaxi.domain.usecase

import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.bltaxi.domain.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveTaxiPositionShould {

    val repository = mock<Repository>()
    val saveTaxiPosition = SaveTaxiPosition(repository)

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
