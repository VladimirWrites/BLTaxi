package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi

class SaveTaxiOrder(private val taxiRepository: TaxiRepository) {
    suspend operator fun invoke(taxis: List<ItemTaxi>) {
        for (i in taxis.indices) {
            this.taxiRepository.setItemPosition(taxis[i].id, i)
        }
    }
}
