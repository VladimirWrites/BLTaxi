package com.vlad1m1r.bltaxi.taxi.data

import android.content.Context
import android.content.pm.PackageManager
import com.vlad1m1r.bltaxi.taxi.domain.usecase.IsViberInstalled

class IsViberInstalledImpl(
    private val context: Context
): IsViberInstalled {

    override operator fun invoke(): Boolean {
        return try {
            context.packageManager.getApplicationInfo("com.viber.voip", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}