package com.vlad1m1r.actions.di

import com.vlad1m1r.actions.ActionExecutorImpl
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActionsModule {

    @Binds
    abstract fun bindActionExecutor(
        actionExecutorImpl: ActionExecutorImpl
    ): ActionExecutor
}