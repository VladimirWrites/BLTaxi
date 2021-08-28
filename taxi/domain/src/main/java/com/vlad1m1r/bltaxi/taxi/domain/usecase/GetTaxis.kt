package com.vlad1m1r.bltaxi.taxi.domain.usecase

import com.vlad1m1r.bltaxi.taxi.domain.Repository

class GetTaxis(private val repository: Repository) {
    suspend operator fun invoke() = repository.getTaxis()
}
