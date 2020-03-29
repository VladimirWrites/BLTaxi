package com.vlad1m1r.actions.executors

import android.content.Intent
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
class SendEmailExecutorShould {

    val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    val sendEmailExecutor = SendEmailExecutor(applicationContext)

    @Test(expected = IllegalArgumentException::class)
    fun throwException_whenWrongEmailFormat() {
        sendEmailExecutor(Action.SendEmailAction("wrong_email_format"))
    }

    @Test
    fun sendEmail() {
        val intent = sendEmailExecutor(Action.SendEmailAction("write@vladimirj.dev"))

        val extrasIntent = intent.extras!![EXTRA_INTENT] as Intent
        assertThat(intent.action).isEqualTo("android.intent.action.CHOOSER")
        assertThat(intent.extras!![EXTRA_TITLE]).isEqualTo("")
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)

        assertThat(extrasIntent.action).isEqualTo(ACTION_SEND)
        assertThat(extrasIntent.type).isEqualTo("message/rfc822")
        assertThat(extrasIntent.extras!![EXTRA_EMAIL]).isEqualTo(arrayOf("write@vladimirj.dev"))
    }

    @Test
    fun sendEmail_whenItHasMailTo() {
        val intent = sendEmailExecutor(Action.SendEmailAction("mailto:write@vladimirj.dev"))

        val extrasIntent = intent.extras!![EXTRA_INTENT] as Intent
        assertThat(intent.action).isEqualTo("android.intent.action.CHOOSER")
        assertThat(intent.extras!![EXTRA_TITLE]).isEqualTo("")
        assertThat(intent.flags).isEqualTo(FLAG_ACTIVITY_NEW_TASK)

        assertThat(extrasIntent.action).isEqualTo(ACTION_SEND)
        assertThat(extrasIntent.type).isEqualTo("message/rfc822")
        assertThat(extrasIntent.extras!![EXTRA_EMAIL]).isEqualTo(arrayOf("mailto:write@vladimirj.dev"))
    }
}