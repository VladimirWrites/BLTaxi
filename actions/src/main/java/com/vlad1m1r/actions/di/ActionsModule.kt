package com.vlad1m1r.actions.di

import com.vlad1m1r.actions.ActionExecutorImpl
import com.vlad1m1r.actions.StartActivity
import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val actionsModule = module {

    single { ShareExecutor(androidContext())}
    single { SendEmailExecutor(androidContext())}
    single { OpenUrlExecutor()}
    single { OpenPlayStoreExecutor(androidContext(), get())}
    single { CallNumberExecutor(androidContext())}
    single { CallNumberOnViberExecutor(androidContext(), get())}

    single { StartActivity(androidContext())}

    single { listOf<Executor>(
        get<ShareExecutor>(),
        get<SendEmailExecutor>(),
        get<OpenPlayStoreExecutor>(),
        get<OpenUrlExecutor>(),
        get<CallNumberExecutor>(),
        get<CallNumberOnViberExecutor>()
    )}

    single<ActionExecutor> { ActionExecutorImpl(get(), get()) }
}