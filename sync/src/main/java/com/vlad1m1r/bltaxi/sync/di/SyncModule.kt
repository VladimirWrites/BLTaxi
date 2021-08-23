package com.vlad1m1r.bltaxi.sync.di

import android.content.Context
import androidx.work.WorkManager
import com.vlad1m1r.bltaxi.sync.SyncTaxisWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {

    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): SyncTaxisWorkManager {
        return SyncTaxisWorkManager(WorkManager.getInstance(context))
    }
}