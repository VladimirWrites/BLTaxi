package com.vlad1m1r.bltaxi.about.data

import android.content.Context
import android.content.Intent
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.Test

class StartActivityShould {

    private val intent = mock<Intent>()
    private val context = mock<Context>()
    private val startActivity = StartActivity(context)

    @Test
    fun startActivity() {
        startActivity(intent)

        verify(context).startActivity(intent)
    }
}