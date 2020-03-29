package com.vlad1m1r.actions.executors

import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.bltaxi.domain.Action
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CallNumberOnViberExecutorShould {

    val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    val openPlayStoreExecutor = mock<OpenPlayStoreExecutor>()
    val callNumberOnViberExecutor = CallNumberOnViberExecutor(applicationContext, openPlayStoreExecutor)

    @Test(expected = IllegalArgumentException::class)
    fun throwException_whenWrongNumberFormat() {
        callNumberOnViberExecutor(Action.CallNumberOnViberAction("wrong_number_format"))
    }
}