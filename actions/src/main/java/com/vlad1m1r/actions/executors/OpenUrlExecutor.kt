package com.vlad1m1r.actions.executors

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vlad1m1r.bltaxi.domain.Action

open class OpenUrlExecutor(private val context: Context) {

    open operator fun invoke(action: Action.OpenUrlAction) {
        var url = action.url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(browserIntent)
    }
}
