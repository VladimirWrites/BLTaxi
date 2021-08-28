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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    fun provideOrderProvider(
        @ApplicationContext context: Context
    ): OrderProvider {
        return OrderProviderImpl(
            PreferenceManager.getDefaultSharedPreferences(context)
        )
    }

    @Provides
    fun provideLanguageProvider(
        @ApplicationContext context: Context
    ): LanguageProvider {
        return LanguageProviderImpl(context)
    }

    @Provides
    fun provideTaxiProviderLocal(
        @ApplicationContext context: Context
    ): TaxiProviderLocal {
        val database = Room.databaseBuilder(
            context,
            TaxiDatabase::class.java, "bltaxi-database"
        ).build()

        return TaxiProviderLocalImpl(database)
    }
}
