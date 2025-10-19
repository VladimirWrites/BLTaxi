package com.vlad1m1r.basedata

import android.content.Context
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.Test

class StringResolverShould {

    private val context = mock<Context> {
        on { getString(any()) }.thenReturn("")
    }
    private val stringResolver = StringResolver(context)

    @Test
    fun getString() {
        stringResolver.getString(10)
        verify(context).getString(10)
    }
}