package com.vlad1m1r.bltaxi.about.data

import android.content.Intent
import android.content.pm.PackageManager
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.Test

class PackageManagerExtShould {

    private val intent = mock<Intent>()
    private val packageManager = mock<PackageManager>()

    @Test
    fun getListOfResolveInfo() {
        packageManager.getListOfResolveInfo(intent)
        verify(packageManager).queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    }
}
