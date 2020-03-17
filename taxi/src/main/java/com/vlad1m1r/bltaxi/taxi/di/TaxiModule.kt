package com.vlad1m1r.bltaxi.taxi.di

import com.vlad1m1r.bltaxi.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.domain.usecase.TaxiInteractor
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taxiModule = module {
    factory { SaveTaxiOrder(get()) }
    factory { TaxiInteractor(get()) }
    factory { ExecuteAction(get()) }
    viewModel { TaxiViewModel(get(), get(), get(), get(), get(), get()) }
}
