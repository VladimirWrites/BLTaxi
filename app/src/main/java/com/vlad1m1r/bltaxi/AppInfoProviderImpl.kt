package com.vlad1m1r.bltaxi

import com.vlad1m1r.bltaxi.about.di.AppInfoProvider

class AppInfoProviderImpl: AppInfoProvider {
    override fun getVersionName() = BuildConfig.VERSION_NAME
    override fun getApplicationId() = BuildConfig.APPLICATION_ID
}