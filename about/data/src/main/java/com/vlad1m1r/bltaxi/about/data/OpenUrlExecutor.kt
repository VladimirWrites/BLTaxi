package com.vlad1m1r.bltaxi.about.data

import android.content.Intent
import android.net.Uri
import com.vlad1m1r.bltaxi.about.domain.Action
import javax.inject.Inject

open class OpenUrlExecutor @Inject constructor(): Executor {
    override fun canHandleAction(action: Action): Boolean {
        return action is Action.OpenUrlAction
    }

    override operator fun invoke(action: Action): Intent {
        val action = action as Action.OpenUrlAction
        var url = action.url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return browserIntent
    }
}
