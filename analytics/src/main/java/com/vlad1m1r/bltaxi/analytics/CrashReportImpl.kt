package com.vlad1m1r.bltaxi.analytics

import android.content.Context
import android.content.SharedPreferences
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric

internal class CrashReportImpl(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
): CrashReport {
    override fun initialize() {
        val keyCrashReport = context.getString(R.string.pref_key_crash_reports)
        if(!sharedPreferences.contains(keyCrashReport)) {
            sharedPreferences.edit()
                .putBoolean(keyCrashReport, true)
                .apply()
            enableCrashReporting(true)
        }
    }

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
