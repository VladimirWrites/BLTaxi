package com.vlad1m1r.bltaxi.about.data

import android.content.Intent
import com.vlad1m1r.bltaxi.about.domain.Action

interface Executor {
    fun canHandleAction(action: Action): Boolean
    operator fun invoke(action: Action): Intent
}