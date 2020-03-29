package com.vlad1m1r.actions

import android.content.Context
import android.content.Intent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class StartActivityShould {

    val intent = mock<Intent>()
    val context = mock<Context>()
    val startActivity = StartActivity(context)

    @Test
    fun startActivity() {
        startActivity(intent)

        verify(context).startActivity(intent)
    }
}