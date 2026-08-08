package com.vlad1m1r.bltaxi

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.sync.SyncTaxisWorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider
import com.vlad1m1r.bltaxi.settings.ui.R as settingsR
import com.vlad1m1r.bltaxi.analytics.R as analyticsR

@HiltAndroidApp
class ApplicationTaxi : Application(), Configuration.Provider {

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var crashReport: CrashReport

    // Provider, not a direct injection: constructing SyncTaxisWorkManager calls
    // WorkManager.getInstance(), which calls back into getWorkManagerConfiguration() below.
    // Resolving it during Hilt's field injection would read workerFactory before it is set.
    @Inject
    lateinit var syncTaxisWorkManager: Provider<SyncTaxisWorkManager>

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        tracker.initialize()

        crashReport.initialize()

        // Safe here: super.onCreate() has finished injecting workerFactory.
        syncTaxisWorkManager.get().start()

        // The Compose theme reads this preference directly (see BLTaxiApp), so it only has to
        // exist. AppCompatDelegate.setDefaultNightMode() no longer applies: the only Activity is
        // a ComponentActivity, which AppCompat's day/night handling does not cover.
        val keyThemePicker = getString(analyticsR.string.pref_key_theme_picker)
        if (!sharedPreferences.contains(keyThemePicker)) {
            sharedPreferences.edit {
                putString(keyThemePicker, getString(settingsR.string.theme_value_default))
            }
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
