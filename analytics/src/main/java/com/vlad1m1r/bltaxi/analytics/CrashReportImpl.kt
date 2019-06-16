package com.vlad1m1r.bltaxi.analytics

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric

internal class CrashReportImpl(private val context: Context): CrashReport {

    override fun enableCrashReporting(enabled: Boolean) {
        val crashlytics = Crashlytics.Builder()
            .core(
                CrashlyticsCore.Builder()
                .disabled(!enabled)
                .build()
            )
            .build()
        Fabric.with(context, crashlytics)
    }
}