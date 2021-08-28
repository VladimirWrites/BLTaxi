package com.vlad1m1r.bltaxi.about.data

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.vlad1m1r.bltaxi.about.domain.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ShareExecutor @Inject constructor(
    @ApplicationContext private val context: Context
) : Executor {
    override fun canHandleAction(action: Action): Boolean {
        return action is Action.ShareAction
    }

    override operator fun invoke(action: Action): Intent {
        val action = action as Action.ShareAction
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, action.url)
        val chooser = Intent.createChooser(sharingIntent, context.getString(R.string.action__share_via))
        chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
        return chooser
    }
}
