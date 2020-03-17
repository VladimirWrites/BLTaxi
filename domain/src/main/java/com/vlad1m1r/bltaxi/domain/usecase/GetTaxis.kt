package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Repository

open class GetTaxis(private val repository: Repository) {
    open suspend operator fun invoke() = repository.getTaxis()
}
