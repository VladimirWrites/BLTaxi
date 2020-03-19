package com.vlad1m1r.bltaxi.local.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.vlad1m1r.bltaxi.local.language.LanguageProviderImpl
import com.vlad1m1r.bltaxi.local.order.OrderProviderImpl
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocalImpl

import com.vlad1m1r.bltaxi.local.database.provideTaxiDatabase
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()); }
    single<OrderProvider> { OrderProviderImpl(get()) }
    single<LanguageProvider> {
        LanguageProviderImpl(
            androidContext()
        )
    }
    single {
        provideTaxiDatabase(
            androidContext()
        )
    }
    single<TaxiProviderLocal> {
        TaxiProviderLocalImpl(
            get()
        )
    }
}
