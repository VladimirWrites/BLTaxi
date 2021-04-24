package com.vlad1m1r.bltaxi.about

interface AppInfoProvider {
    fun getVersionName(): String
    fun getApplicationId(): String
}