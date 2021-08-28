package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult

class GetOrderedTaxiList(
    private val getTaxis: GetTaxis,
    private val setOrderOfTaxis: SetOrderOfTaxis
) {

    suspend operator fun invoke(): TaxisResult {
        val taxis = getTaxis()
        return when(taxis) {
            is TaxisResult.Success -> return TaxisResult.Success(setOrderOfTaxis(taxis.list))
            is TaxisResult.Error -> taxis
        }
    }
}
