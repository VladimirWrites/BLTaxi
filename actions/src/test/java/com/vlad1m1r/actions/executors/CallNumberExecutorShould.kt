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
class CallNumberExecutorShould {

    val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    val callNumberExecutor = CallNumberExecutor(applicationContext)

    @Test(expected = IllegalArgumentException::class)
    fun throwException_whenWrongNumberFormat() {
        callNumberExecutor(Action.CallNumberAction("wrong_number_format"))
    }

    @Test
    fun callNumber() {
        val intent = callNumberExecutor(Action.CallNumberAction("+4912345678900"))

        val extrasIntent = intent.extras!![EXTRA_INTENT] as Intent
        assertThat(intent.action).isEqualTo("android.intent.action.CHOOSER")
        assertThat(intent.extras!![EXTRA_TITLE]).isEqualTo(applicationContext.getString(R.string.action__call_number))
        assertThat(extrasIntent.action).isEqualTo(ACTION_DIAL)
        assertThat(extrasIntent.data!!.scheme).isEqualTo("tel")
        assertThat(extrasIntent.data!!.schemeSpecificPart).isEqualTo("+4912345678900")
    }
}