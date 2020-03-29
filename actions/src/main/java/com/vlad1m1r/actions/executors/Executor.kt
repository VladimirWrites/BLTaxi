package com.vlad1m1r.actions.executors

import android.content.Intent
import com.vlad1m1r.bltaxi.domain.Action

interface Executor {
    fun canHandleAction(action: Action): Boolean
    operator fun invoke(action: Action): Intent
}