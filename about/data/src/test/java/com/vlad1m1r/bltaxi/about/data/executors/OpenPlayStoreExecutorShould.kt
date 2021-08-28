package com.vlad1m1r.bltaxi.about.data.executors

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.about.data.OpenPlayStoreExecutor
import com.vlad1m1r.bltaxi.about.data.OpenUrlExecutor
import com.vlad1m1r.bltaxi.about.domain.Action
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class OpenPlayStoreExecutorShould {

    private val packageManager = mock<PackageManager>()
    private val context = mock<Context> {
        on { packageManager }.thenReturn(packageManager)
    }
    private val openUrlExecutor = mock<OpenUrlExecutor> {
        on { invoke(any()) }.thenReturn(Intent())
    }
    private val openPlayStoreExecutor = OpenPlayStoreExecutor(context, openUrlExecutor)

    @Test
    fun openPlayStore_whenPlayStoreIsAvailable() {
        whenever(packageManager.queryIntentActivities(any(), any())).thenReturn(listOf(ResolveInfo()))

        val intent = openPlayStoreExecutor(Action.OpenPlayStoreAction("com.vlad1m1r.bltaxi"))

        assertThat(intent.action).isEqualTo(ACTION_VIEW)
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)
        assertThat(intent.data!!.scheme).isEqualTo("market")
        assertThat(intent.data!!.host).isEqualTo("details")
        assertThat(intent.data!!.getQueryParameter("id")).isEqualTo("com.vlad1m1r.bltaxi")
    }

    @Test
    fun openPlayStoreOnWeb_whenPlayStoreIsNotAvailable() {
        whenever(packageManager.queryIntentActivities(any(), any())).thenReturn(emptyList())

        openPlayStoreExecutor(Action.OpenPlayStoreAction("com.vlad1m1r.bltaxi"))

        verify(openUrlExecutor).invoke(Action.OpenUrlAction("https://play.google.com/store/apps?id=com.vlad1m1r.bltaxi"))
    }
}