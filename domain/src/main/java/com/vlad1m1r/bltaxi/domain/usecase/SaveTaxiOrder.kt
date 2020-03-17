package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

open class SaveTaxiOrder(private val repository: Repository) {
    open suspend operator fun invoke(taxis: List<ItemTaxi>) {
        for (i in taxis.indices) {
            this.repository.setItemPosition(taxis[i].id, i)
        }
    }
}
