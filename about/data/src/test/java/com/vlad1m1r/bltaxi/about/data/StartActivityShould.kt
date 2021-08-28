package com.vlad1m1r.bltaxi.about.data

import android.content.Context
import android.content.Intent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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