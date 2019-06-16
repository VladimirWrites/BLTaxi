package com.vlad1m1r.actions.di

import com.vlad1m1r.actions.ActionExecutorImpl
import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val actionsModule = module {

    single { ShareExecutor(androidContext())}
    single { SendEmailExecutor(androidContext())}
    single { OpenUrlExecutor(androidContext())}
    single { OpenPlayStoreExecutor(androidContext(), get())}
    single { CallNumberExecutor(androidContext())}
    single { CallNumberOnViberExecutor(androidContext(), get())}

    single<ActionExecutor> { ActionExecutorImpl(get(), get(), get(), get(), get(), get()) }
}