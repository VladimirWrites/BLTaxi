package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository

class GetTaxiPosition(private val taxiRepository: TaxiRepository) {
    suspend operator fun invoke(taxiId: Long) = taxiRepository.getItemPosition(taxiId)
}
