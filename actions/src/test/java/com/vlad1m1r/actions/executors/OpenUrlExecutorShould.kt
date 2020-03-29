package com.vlad1m1r.actions.executors

import android.content.Intent.*
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.Action
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class OpenUrlExecutorShould {

    val openUrlExecutor = OpenUrlExecutor()

    @Test
    fun openUrl() {
        val intent = openUrlExecutor(Action.OpenUrlAction("https://www.vladimirj.dev"))

        assertThat(intent.action).isEqualTo(ACTION_VIEW)
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)
        assertThat(intent.data!!.scheme).isEqualTo("https")
        assertThat(intent.data!!.host).isEqualTo("www.vladimirj.dev")
    }

    @Test
    fun openUrlAndAppendHttp_ifNotThere() {
        val intent = openUrlExecutor(Action.OpenUrlAction("www.vladimirj.dev"))

        assertThat(intent.action).isEqualTo(ACTION_VIEW)
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)
        assertThat(intent.data!!.scheme).isEqualTo("http")
        assertThat(intent.data!!.host).isEqualTo("www.vladimirj.dev")
    }
}