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
            val uri = Uri.parse(context.getString(R.string.action__tel) + action.phoneNumber)
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = uri
            val resolveInfo = getViberResolveInfo(context.packageManager, intent)
            if (resolveInfo != null) {
                intent = resolveInfo.getIntent()
                intent.data = uri
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
        val activityInfo = this.activityInfo
        val name = ComponentName(
            activityInfo.applicationInfo.packageName,
            activityInfo.name
        )
        val intent = Intent(Intent.ACTION_DIAL)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        intent.component = name

        return intent
    }
}
