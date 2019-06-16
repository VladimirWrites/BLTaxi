package com.vlad1m1r.bltaxi.about.di

interface AppInfoProvider {
    fun getVersionName(): String
    fun getApplicationId(): String
}