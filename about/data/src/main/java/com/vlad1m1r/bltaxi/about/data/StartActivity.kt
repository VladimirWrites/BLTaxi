package com.vlad1m1r.bltaxi.about.data

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StartActivity @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(intent: Intent) {
        context.startActivity(intent)
    }
}