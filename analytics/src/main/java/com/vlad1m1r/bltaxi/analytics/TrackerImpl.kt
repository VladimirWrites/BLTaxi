package com.vlad1m1r.bltaxi.analytics

import com.google.firebase.analytics.FirebaseAnalytics

internal class TrackerImpl(private val firebaseAnalytics: FirebaseAnalytics): Tracker {

    override fun track(event: Event) {
        firebaseAnalytics.logEvent(event.name, event.data)
    }

    override fun enableTracking(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}
