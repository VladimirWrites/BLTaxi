package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository

class GetTaxis(private val repository: Repository) {
    suspend operator fun invoke() = repository.getTaxis()
}
