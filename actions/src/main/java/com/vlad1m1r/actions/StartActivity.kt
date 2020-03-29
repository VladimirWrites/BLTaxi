package com.vlad1m1r.actions

import android.content.Context
import android.content.Intent

class StartActivity(private val context: Context) {
    operator fun invoke(intent: Intent) {
        context.startActivity(intent)
    }
}