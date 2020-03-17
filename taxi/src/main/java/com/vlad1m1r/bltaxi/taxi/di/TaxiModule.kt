package com.vlad1m1r.bltaxi.taxi.di

import com.vlad1m1r.bltaxi.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.domain.usecase.GetTaxiPosition
import com.vlad1m1r.bltaxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.domain.usecase.TaxiInteractor
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taxiModule = module {
    factory { GetTaxiPosition(get()) }
    factory { SaveTaxiOrder(get()) }
    factory { TaxiInteractor(get(), get()) }
    factory { ExecuteAction(get()) }
    viewModel { TaxiViewModel(get(), get(), get(), get(), get(), get()) }
}
