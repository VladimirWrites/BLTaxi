package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository

class GetTaxis(private val taxiRepository: TaxiRepository) {
    suspend operator fun invoke() = taxiRepository.getTaxis()
}
