package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.TaxisResult

class GetOrderedTaxiList(
    private val getTaxis: GetTaxis,
    private val orderTaxis: OrderTaxis) {

    suspend operator fun invoke(): TaxisResult {
        val taxis = getTaxis()
        return when(taxis) {
            is TaxisResult.Success -> return TaxisResult.Success(orderTaxis(taxis.list))
            is TaxisResult.Error -> taxis
        }
    }
}
