package com.vlad1m1r.bltaxi.about.ui

interface AppInfoProvider {
    fun getVersionName(): String
    fun getApplicationId(): String
}