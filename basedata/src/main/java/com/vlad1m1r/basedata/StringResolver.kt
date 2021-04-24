package com.vlad1m1r.basedata

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResolver @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)
}
