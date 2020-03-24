package com.vlad1m1r.bltaxi.analytics

interface CrashReport {
    fun initialize()
    fun enableCrashReporting(enabled: Boolean)
}
