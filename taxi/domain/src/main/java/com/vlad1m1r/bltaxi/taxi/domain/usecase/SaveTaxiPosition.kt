package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository

class SaveTaxiPosition(private val taxiRepository: TaxiRepository) {
    suspend operator fun invoke(taxiId: Long, position: Int) = taxiRepository.setItemPosition(taxiId, position)
}
