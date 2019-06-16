package com.vlad1m1r.bltaxi.analytics

interface Tracker {
    fun track(event: Event)
    fun enableTracking(enabled: Boolean)
}