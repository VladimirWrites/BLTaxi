package com.vlad1m1r.bltaxi.analytics.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.CrashReportImpl
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.TrackerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    fun provideTracker(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): Tracker {
        return TrackerImpl(
            FirebaseAnalytics.getInstance(context),
            context,
            sharedPreferences
        )
    }

    @Provides
    fun provideCrashReport(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): CrashReport {
        return CrashReportImpl(
            context,
            FirebaseCrashlytics.getInstance(),
            sharedPreferences
        )
    }
}
