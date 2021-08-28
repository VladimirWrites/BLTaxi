package com.vlad1m1r.bltaxi.about.data

import android.content.Intent
import android.content.pm.PackageManager
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
