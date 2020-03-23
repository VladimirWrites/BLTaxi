package com.vlad1m1r.basedata

import android.content.Context
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class StringResolverShould {

    val context = mock<Context>() {
        on { getString(any()) }.thenReturn("")
    }
    val stringResolver = StringResolver(context)

    @Test
    fun getString() {
        stringResolver.getString(10)
        verify(context).getString(10)
    }
}