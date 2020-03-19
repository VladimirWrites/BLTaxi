package com.vlad1m1r.bltaxi.remote.di

import com.vlad1m1r.bltaxi.remote.*
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemoteImpl
import com.vlad1m1r.bltaxi.remote.TaxiService
import com.vlad1m1r.bltaxi.remote.getTaxiService
import com.vlad1m1r.bltaxi.remote.provideRetrofit
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val remoteModule = module {

    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single{ provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single<TaxiService> { getTaxiService(get()) }
    single<TaxiProviderRemote> { TaxiProviderRemoteImpl(get()) }
}
