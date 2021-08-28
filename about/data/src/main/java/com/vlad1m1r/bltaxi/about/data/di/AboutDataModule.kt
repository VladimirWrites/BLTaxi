package com.vlad1m1r.bltaxi.about.data.di

import com.vlad1m1r.bltaxi.about.data.ActionExecutorImpl
import com.vlad1m1r.bltaxi.about.domain.ActionExecutor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AboutDataModule {

    @Binds
    abstract fun bindActionExecutor(
        actionExecutorImpl: ActionExecutorImpl
    ): ActionExecutor
}