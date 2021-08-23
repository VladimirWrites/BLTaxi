package com.vlad1m1r.bltaxi

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.sync.SyncTaxisWorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ApplicationTaxi : Application(), Configuration.Provider {

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var crashReport: CrashReport

    @Inject
    lateinit var syncTaxisWorkManager: SyncTaxisWorkManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        tracker.initialize()

        crashReport.initialize()

        syncTaxisWorkManager.start()

        val keyThemePicker = getString(R.string.pref_key_theme_picker)

        if (!sharedPreferences.contains(keyThemePicker)) {
            sharedPreferences.edit()
                .putString(keyThemePicker, getString(R.string.theme_value_default))
                .apply()
            setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            val theme = sharedPreferences.getString(keyThemePicker, "")
            val newMode = when (theme) {
                getString(R.string.theme_value_dark) -> MODE_NIGHT_YES
                getString(R.string.theme_value_light) -> MODE_NIGHT_NO
                getString(R.string.theme_value_default) -> MODE_NIGHT_FOLLOW_SYSTEM
                else -> throw IllegalArgumentException("Mode with value: $theme is not supported")
            }
            setDefaultNightMode(newMode)
        }
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}
