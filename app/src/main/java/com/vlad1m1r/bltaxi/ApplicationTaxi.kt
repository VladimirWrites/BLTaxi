package com.vlad1m1r.bltaxi

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate.*
import com.vlad1m1r.actions.di.actionsModule
import com.vlad1m1r.basedata.di.baseDataModule
import com.vlad1m1r.bltaxi.about.di.aboutModule
import com.vlad1m1r.bltaxi.analytics.CrashReport
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.di.analyticsModule
import com.vlad1m1r.bltaxi.di.Logger
import com.vlad1m1r.bltaxi.di.appModule
import com.vlad1m1r.bltaxi.local.di.localModule
import com.vlad1m1r.bltaxi.remote.di.remoteModule
import com.vlad1m1r.bltaxi.repository.di.repositoryModule
import com.vlad1m1r.bltaxi.settings.di.settingsModule
import com.vlad1m1r.bltaxi.shortcuts.di.shortcutsModule
import com.vlad1m1r.bltaxi.taxi.di.taxiModule
import com.vladimir.bltaxi.sync.SyncTaxisWorkManager
import com.vladimir.bltaxi.sync.di.syncModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationTaxi : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(Logger())
            androidContext(this@ApplicationTaxi)
            modules(
                mutableListOf(
                    appModule,
                    baseDataModule,
                    localModule,
                    remoteModule,
                    repositoryModule,
                    actionsModule,
                    taxiModule,
                    aboutModule,
                    analyticsModule,
                    settingsModule,
                    syncModule
                ).apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                        add(shortcutsModule)
                    }
                }
            )
        }

        val tracker: Tracker by inject()
        tracker.initialize()

        val crashReport: CrashReport by inject()
        crashReport.initialize()

        val syncTaxisWorkManager: SyncTaxisWorkManager by inject()
        syncTaxisWorkManager.start()

        val sharedPreferences: SharedPreferences by inject()
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
}
