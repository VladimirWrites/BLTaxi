package com.vlad1m1r.actions.executors

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.vlad1m1r.actions.R
import com.vlad1m1r.bltaxi.domain.Action

class ShareExecutor(private val context: Context) {
    operator fun invoke(action: Action.ShareAction) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, action.url)
        val chooser = Intent.createChooser(sharingIntent, context.getString(R.string.action__share_via))
        chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}