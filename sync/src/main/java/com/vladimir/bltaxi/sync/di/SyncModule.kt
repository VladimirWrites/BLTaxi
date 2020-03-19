package com.vladimir.bltaxi.sync.di

import androidx.work.WorkManager
import com.vladimir.bltaxi.sync.SyncTaxis
import com.vladimir.bltaxi.sync.SyncTaxisWorkManager
import com.vladimir.bltaxi.sync.SyncTaxisWorker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val syncModule = module {
    single { SyncTaxis(get(), get(), get()) }
    single { SyncTaxisWorker(androidContext(), get(), get()) }
    single { WorkManager.getInstance(androidContext()) }
    single { SyncTaxisWorkManager(get()) }
}
