package com.vlad1m1r.bltaxi.taxi.di

import com.vlad1m1r.bltaxi.domain.interactor.ActionInteractor
import com.vlad1m1r.bltaxi.domain.interactor.TaxiInteractor
import com.vlad1m1r.bltaxi.taxi.TaxiViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taxiModule = module {
    factory { TaxiInteractor(get()) }
    factory { ActionInteractor(get()) }
    viewModel { TaxiViewModel(get(), get(), get(), get()) }
}