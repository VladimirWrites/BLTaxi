package com.vlad1m1r.bltaxi

import com.vlad1m1r.bltaxi.about.ui.AppInfoProvider
import javax.inject.Inject

class AppInfoProviderImpl @Inject constructor(): AppInfoProvider {
    override fun getVersionName() = BuildConfig.VERSION_NAME
    override fun getApplicationId() = BuildConfig.APPLICATION_ID
}