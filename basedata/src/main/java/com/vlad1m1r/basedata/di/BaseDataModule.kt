package com.vlad1m1r.basedata.di

import com.vlad1m1r.basedata.StringResolver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val baseDataModule = module {
    single { StringResolver(androidContext()) }
}