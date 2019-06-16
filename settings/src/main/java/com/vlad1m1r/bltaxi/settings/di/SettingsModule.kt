package com.vlad1m1r.bltaxi.settings.di

import com.vlad1m1r.bltaxi.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get(), get(), get()) }
}