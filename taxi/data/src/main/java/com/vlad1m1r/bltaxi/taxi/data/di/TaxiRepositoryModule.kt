package com.vlad1m1r.bltaxi.taxi.data.di

import android.content.Context
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import com.vlad1m1r.bltaxi.taxi.data.IsViberInstalledImpl
import com.vlad1m1r.bltaxi.taxi.data.TaxiRepositoryImpl
import com.vlad1m1r.bltaxi.taxi.domain.usecase.IsViberInstalled
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TaxiRepositoryModule {

    @Provides
    fun provideTaxiRepository(
        orderProvider: OrderProvider,
        taxiProviderLocal: TaxiProviderLocal,
        taxiProviderRemote: TaxiProviderRemote,
        languageProvider: LanguageProvider
    ): TaxiRepository {
        return TaxiRepositoryImpl(
            orderProvider,
            taxiProviderLocal,
            taxiProviderRemote,
            languageProvider
        )
    }

    @Provides
    fun provideIsViberInstalled(
        @ApplicationContext context: Context
    ): IsViberInstalled {
        return IsViberInstalledImpl(
            context
        )
    }
}