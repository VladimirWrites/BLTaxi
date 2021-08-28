package com.vlad1m1r.bltaxi.about.data

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.vlad1m1r.bltaxi.about.domain.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OpenPlayStoreExecutor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openUrlExecutor: OpenUrlExecutor
) : Executor {
    override fun canHandleAction(action: Action): Boolean {
        return action is Action.OpenPlayStoreAction
    }

    override operator fun invoke(action: Action): Intent {
        val action = action as Action.OpenPlayStoreAction
        val storeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${action.applicationId}"))
        if (context.packageManager.getListOfResolveInfo(storeIntent).isNotEmpty()) {
            storeIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            return storeIntent
        } else {
            return openPlayStoreOnWeb(action)
        }
    }

    private fun openPlayStoreOnWeb(action: Action.OpenPlayStoreAction): Intent {
        return openUrlExecutor(Action.OpenUrlAction("https://play.google.com/store/apps?id=${action.applicationId}"))
    }
}
