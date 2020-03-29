package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository

class SaveTaxiPosition(private val repository: Repository) {
    suspend operator fun invoke(taxiId: Long, position: Int) = repository.setItemPosition(taxiId, position)
}
