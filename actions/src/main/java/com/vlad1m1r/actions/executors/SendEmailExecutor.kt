package com.vlad1m1r.actions.executors

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Patterns
import com.vlad1m1r.bltaxi.domain.Action

internal open class SendEmailExecutor(private val context: Context) {
    open operator fun invoke(action: Action.SendEmailAction) {
        val email =  action.email
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()
            || (email.startsWith("mailto:") && Patterns.EMAIL_ADDRESS.matcher(email.substring(7)).matches())
        ) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            val chooser = Intent.createChooser(intent, "")
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } else {
            throw IllegalArgumentException("Email ${action.email} has a wrong format")
        }
    }
}
