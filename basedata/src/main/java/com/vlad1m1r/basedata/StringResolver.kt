package com.vlad1m1r.basedata

import android.content.Context
import androidx.annotation.StringRes

class StringResolver(private val context: Context) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)
}