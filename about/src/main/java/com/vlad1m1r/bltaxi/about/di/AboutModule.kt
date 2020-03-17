package com.vlad1m1r.bltaxi.about.di

import com.vlad1m1r.bltaxi.about.AboutViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aboutModule = module {
    viewModel { AboutViewModel(get(), get(), get()) }
}
