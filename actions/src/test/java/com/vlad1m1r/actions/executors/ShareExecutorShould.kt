package com.vlad1m1r.actions.executors

import android.content.Intent
import android.content.Intent.*
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.actions.R
import com.vlad1m1r.bltaxi.domain.Action
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ShareExecutorShould {

    val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    val shareExecutor = ShareExecutor(applicationContext)

    @Test
    fun share() {
        val intent = shareExecutor(Action.ShareAction("some data to share"))

        val extrasIntent = intent.extras!![EXTRA_INTENT] as Intent
        assertThat(intent.action).isEqualTo("android.intent.action.CHOOSER")
        assertThat(intent.extras!![EXTRA_TITLE]).isEqualTo(applicationContext.getString(R.string.action__share_via))
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)

        assertThat(extrasIntent.action).isEqualTo(ACTION_SEND)
        assertThat(extrasIntent.type).isEqualTo("text/plain")
        assertThat(extrasIntent.extras!![EXTRA_TEXT]).isEqualTo("some data to share")
    }
}