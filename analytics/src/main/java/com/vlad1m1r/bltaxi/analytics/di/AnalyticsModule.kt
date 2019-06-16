package com.vlad1m1r.bltaxi.analytics.di

import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.CrashReportImpl
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.TrackerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {
    single<Tracker> { TrackerImpl(androidContext()) }
    single<CrashReport> { CrashReportImpl(androidContext()) }
}