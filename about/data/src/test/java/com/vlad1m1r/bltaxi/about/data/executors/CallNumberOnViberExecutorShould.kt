package com.vlad1m1r.bltaxi.about.data.executors

import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.bltaxi.about.data.CallNumberOnViberExecutor
import com.vlad1m1r.bltaxi.about.data.OpenPlayStoreExecutor
import com.vlad1m1r.bltaxi.about.domain.Action
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CallNumberOnViberExecutorShould {

    private val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    private val openPlayStoreExecutor = mock<OpenPlayStoreExecutor>()
    private val callNumberOnViberExecutor = CallNumberOnViberExecutor(applicationContext, openPlayStoreExecutor)

    @Test(expected = IllegalArgumentException::class)
    fun throwException_whenWrongNumberFormat() {
        callNumberOnViberExecutor(Action.CallNumberOnViberAction("wrong_number_format"))
    }
}