package com.vlad1m1r.bltaxi.repository.di

import com.vlad1m1r.bltaxi.domain.Repository
import com.vlad1m1r.bltaxi.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get(), get(), get()) }
}