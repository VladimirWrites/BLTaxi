package com.vlad1m1r.bltaxi.di

import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.AppInfoProviderImpl
import com.vlad1m1r.bltaxi.Navigator
import com.vlad1m1r.bltaxi.about.di.AppInfoProvider
import com.vlad1m1r.bltaxi.taxi.TaxiNavigator
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    single<TaxiNavigator> { Navigator()}
    single { CoroutineDispatcherProvider(main = Dispatchers.Main, io = Dispatchers.IO)}
    single<AppInfoProvider> { AppInfoProviderImpl() }
}