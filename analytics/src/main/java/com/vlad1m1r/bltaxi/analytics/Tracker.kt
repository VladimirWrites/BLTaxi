package com.vlad1m1r.bltaxi.analytics

interface Tracker {
    fun initialize()
    fun track(event: Event)
    fun enableTracking(enabled: Boolean)
}
