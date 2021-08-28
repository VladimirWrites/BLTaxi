package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi

class OrderTaxis(
    private val getTaxiPosition: GetTaxiPosition,
    private val saveTaxiOrder: SaveTaxiOrder
) {
    suspend operator fun invoke(taxis: List<ItemTaxi>): List<ItemTaxi> {

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
