package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.Repository
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi

class SaveTaxiOrder(private val repository: Repository) {
    suspend operator fun invoke(taxis: List<ItemTaxi>) {
        for (i in taxis.indices) {
            this.repository.setItemPosition(taxis[i].id, i)
        }
    }
}
