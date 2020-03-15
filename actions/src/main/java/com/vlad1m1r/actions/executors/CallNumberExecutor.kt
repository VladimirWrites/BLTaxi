package com.vlad1m1r.actions.executors

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Patterns
import com.vlad1m1r.actions.R
import com.vlad1m1r.bltaxi.domain.Action

internal open class CallNumberExecutor(private val context: Context) {

    open operator fun invoke(action: Action.CallNumberAction) {
        if (Patterns.PHONE.matcher(action.phoneNumber).matches()) {
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:${action.phoneNumber}")
            val chooser = Intent.createChooser(i, context.resources.getString(R.string.action__call_number))
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } else {
            throw IllegalArgumentException("Phone number ${action.phoneNumber} has a wrong format")
        }
    }
}
