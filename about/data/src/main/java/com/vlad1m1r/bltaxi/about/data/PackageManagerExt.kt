package com.vlad1m1r.bltaxi.about.data

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

internal fun PackageManager.getListOfResolveInfo(intent: Intent): List<ResolveInfo> {
    return this.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
}
