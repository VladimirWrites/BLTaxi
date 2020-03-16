package com.vlad1m1r.actions.executors

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.vlad1m1r.bltaxi.domain.Action

open class OpenPlayStoreExecutor(private val context: Context, private val openUrlExecutor: OpenUrlExecutor) {

    open operator fun invoke(action: Action.OpenPlayStoreAction) {

        val storeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${action.applicationId}"))
        if (context.packageManager.getListOfResolveInfo(storeIntent).isNotEmpty()) {
            try {
                storeIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(storeIntent)
            } catch (e: Exception) {
                openPlayStoreOnWeb(action)
            }

        } else {
            openPlayStoreOnWeb(action)
        }

    }
    private fun openPlayStoreOnWeb(action: Action.OpenPlayStoreAction) {
        openUrlExecutor(Action.OpenUrlAction("https://play.google.com/store/apps?id=${action.applicationId}"))
    }
}
