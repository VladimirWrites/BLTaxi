package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository

class GetTaxiPosition(private val repository: Repository) {
    suspend operator fun invoke(taxiId: Long) = repository.getItemPosition(taxiId)
}
