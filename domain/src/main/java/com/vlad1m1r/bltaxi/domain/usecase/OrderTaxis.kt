package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

open class OrderTaxis(
    private val getTaxiPosition: GetTaxiPosition,
    private val saveTaxiOrder: SaveTaxiOrder
) {
    open suspend operator fun invoke(taxis: List<ItemTaxi>): List<ItemTaxi> {

        val orderedList: MutableList<ItemTaxi?> = taxis.map { null }.toMutableList()
        var shouldSaveTaxiOrder = false
        for (taxi in taxis) {
            val position = getTaxiPosition(taxi.id)

            if (position >= taxis.size || position < 0) {
                orderedList.add(taxi)
                shouldSaveTaxiOrder = true
            } else {
                orderedList[position] = taxi
            }
        }
        val result = orderedList.filterNotNull()
        if(shouldSaveTaxiOrder) {
            saveTaxiOrder(result)
        }
        return result
    }
}
