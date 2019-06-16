package com.vlad1m1r.bltaxi.local.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.vlad1m1r.bltaxi.local.LanguageProviderImpl
import com.vlad1m1r.bltaxi.local.OrderImpl
import com.vlad1m1r.bltaxi.local.TaxiProviderLocalImpl
import com.vlad1m1r.bltaxi.local.database.TaxiDatabase
import com.vlad1m1r.bltaxi.local.database.provideTaxiDatabase
import com.vlad1m1r.bltaxi.repository.LanguageProvider
import com.vlad1m1r.bltaxi.repository.Order
import com.vlad1m1r.bltaxi.repository.TaxiProviderLocal
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()); }
    single<Order> { OrderImpl(get())}
    single<LanguageProvider> { LanguageProviderImpl(androidContext()) }
    single<TaxiDatabase> {
        provideTaxiDatabase(
            androidContext()
        )
    }
    single<TaxiProviderLocal> { TaxiProviderLocalImpl(get()) }
}