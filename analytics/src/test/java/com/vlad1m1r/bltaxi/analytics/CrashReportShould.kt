package com.vlad1m1r.bltaxi.analytics

import android.app.Activity
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CrashReportShould {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val firebaseCrashlytics = mock<FirebaseCrashlytics>()
    val sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE);
    val crashReport: CrashReport = CrashReportImpl(context, firebaseCrashlytics, sharedPreferences)

    @Test
    fun initialize_whenNotInitialized() {
        val keyCrashReport = context.getString(R.string.pref_key_crash_reports)
        sharedPreferences.edit().clear()

        crashReport.initialize()

        assertThat(sharedPreferences.getBoolean(keyCrashReport, false)).isTrue()
    }
}