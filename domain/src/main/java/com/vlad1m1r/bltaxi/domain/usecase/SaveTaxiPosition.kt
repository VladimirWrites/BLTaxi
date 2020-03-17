package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository

open class SaveTaxiPosition(private val repository: Repository) {
    open suspend operator fun invoke(taxiId: Long, position: Int) = repository.setItemPosition(taxiId, position)
}
