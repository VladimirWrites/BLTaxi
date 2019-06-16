package com.vlad1m1r.bltaxi.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

internal class TrackerImpl(context: Context): Tracker {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun track(event: Event) {
        firebaseAnalytics.logEvent(event.name, event.data)
    }

    override fun enableTracking(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}