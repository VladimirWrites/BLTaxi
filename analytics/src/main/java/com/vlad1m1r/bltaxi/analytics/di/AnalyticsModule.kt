package com.vlad1m1r.bltaxi.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.CrashReportImpl
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.TrackerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(androidContext()) }
    single<Tracker> { TrackerImpl(get()) }
    single<CrashReport> { CrashReportImpl(androidContext()) }
}
