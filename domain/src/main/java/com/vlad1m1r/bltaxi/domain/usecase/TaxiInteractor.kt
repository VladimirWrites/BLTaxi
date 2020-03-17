package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.domain.Repository
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

class TaxiInteractor(private val repository: Repository) {

    suspend operator fun invoke(): TaxisResult {
        return when(val taxis = repository.getTaxis()) {
            is TaxisResult.Success -> return TaxisResult.Success(setupOrder(taxis.list))
            is TaxisResult.Error -> taxis
        }
    }

    private fun setupOrder(itemTaxiArrayList: List<ItemTaxi>): List<ItemTaxi> {
        val result = mutableListOf<ItemTaxi>()

        for (taxi in itemTaxiArrayList) {
            result.add(taxi)
        }

        for (taxi in itemTaxiArrayList) {
            val position = this.repository.getItemPosition(taxi.id)

            //fallback
            if (position >= itemTaxiArrayList.size) {
                for (i in itemTaxiArrayList.indices) {
                    this.repository.setItemPosition(itemTaxiArrayList[i].id, i)
                }
                result.clear()
                for (temp in itemTaxiArrayList) {
                    result.add(temp)
                }
                return result
            } else {
                result[position] = taxi
            }
        }
        return result
    }
}
