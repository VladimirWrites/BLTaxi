package com.vlad1m1r.bltaxi.taxi.data.di

import com.vlad1m1r.bltaxi.taxi.domain.Repository
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import com.vlad1m1r.bltaxi.taxi.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun bindRepository(
        orderProvider: OrderProvider,
        taxiProviderLocal: TaxiProviderLocal,
        taxiProviderRemote: TaxiProviderRemote,
        languageProvider: LanguageProvider
    ): Repository {
        return RepositoryImpl(
            orderProvider,
            taxiProviderLocal,
            taxiProviderRemote,
            languageProvider
        )
    }
}