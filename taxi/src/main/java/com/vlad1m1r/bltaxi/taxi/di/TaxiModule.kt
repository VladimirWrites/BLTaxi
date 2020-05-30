package com.vlad1m1r.bltaxi.taxi.di

import com.vlad1m1r.bltaxi.domain.usecase.*
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taxiModule = module {
    factory { GetTaxis(get()) }
    factory { SaveTaxiOrder(get()) }
    factory { OrderTaxis(get(), get()) }
    factory { SaveTaxiPosition(get()) }
    factory { GetTaxiPosition(get()) }
    factory { GetOrderedTaxiList(get(), get()) }
    factory { ExecuteAction(get()) }
    viewModel { TaxiViewModel(get(), getOrNull(), get(), get(), get(), get()) }
}
