package com.vlad1m1r.bltaxi.di

import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Event
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.di.AnalyticsModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Firebase is not initialized in Robolectric, so the real [AnalyticsModule] blows up as soon as
 * anything injects [Tracker] or [CrashReport]. Tests get no-op implementations instead.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AnalyticsModule::class]
)
object FakeAnalyticsModule {

    @Provides
    @Singleton
    fun provideTracker(): Tracker = object : Tracker {
        override fun initialize() = Unit
        override fun track(event: Event) = Unit
        override fun enableTracking(enabled: Boolean) = Unit
    }

    @Provides
    @Singleton
    fun provideCrashReport(): CrashReport = object : CrashReport {
        override fun initialize() = Unit
        override fun enableCrashReporting(enabled: Boolean) = Unit
    }
}
