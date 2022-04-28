package com.vlad1m1r.bltaxi.about.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.util.Patterns
import com.vlad1m1r.bltaxi.about.domain.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CallNumberOnViberExecutor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openPlayStoreExecutor: OpenPlayStoreExecutor
) : Executor {
    override fun canHandleAction(action: Action): Boolean {
        return action is Action.CallNumberOnViberAction
    }

    override operator fun invoke(action: Action): Intent {
        val action = action as Action.CallNumberOnViberAction
        if (Patterns.PHONE.matcher(action.phoneNumber).matches()) {
            val uri = Uri.parse("viber://contact?number=${action.phoneNumber}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            val resolveInfo = getViberResolveInfo(context.packageManager, intent)
            if (resolveInfo != null) {
                return intent
            } else {
                return openPlayStoreExecutor(Action.OpenPlayStoreAction("com.viber.voip"))
            }
        } else {
            throw IllegalArgumentException("Phone number ${action.phoneNumber} has a wrong format")
        }
    }

    private fun getViberResolveInfo(packageManager: PackageManager, intent: Intent): ResolveInfo? {
        val activities = packageManager.getListOfResolveInfo(intent)

        for (resolveInfo in activities) {
            if (resolveInfo.activityInfo.applicationInfo.packageName == "com.viber.voip") {
                return resolveInfo
            }
        }
        return null
    }

    private fun ResolveInfo.getIntent(): Intent {

        val i = Intent(Intent.ACTION_DIAL)
        val chooser = Intent.createChooser(i, context.resources.getString(R.string.action__call_number))
        chooser.flags = FLAG_ACTIVITY_NEW_TASK
        return chooser

    }
}
