package com.vlad1m1r.bltaxi.local.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.vlad1m1r.bltaxi.local.database.TaxiDatabase
import com.vlad1m1r.bltaxi.local.language.LanguageProviderImpl
import com.vlad1m1r.bltaxi.local.order.OrderProviderImpl
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocalImpl
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun bindSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    fun providesTaxiDatabase(
        @ApplicationContext context: Context
    ): TaxiDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaxiDatabase::class.java, "bltaxi-database"
        ).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProvidersModule {

    @Binds
    abstract fun bindOrderProvider(
        orderProviderImpl: OrderProviderImpl
    ): OrderProvider

    @Binds
    abstract fun bindLanguageProvider(
        languageProviderImpl: LanguageProviderImpl
    ): LanguageProvider

    @Binds
    abstract fun bindTaxiProviderLocal(
        taxiProviderLocalImpl: TaxiProviderLocalImpl
    ): TaxiProviderLocal
}
