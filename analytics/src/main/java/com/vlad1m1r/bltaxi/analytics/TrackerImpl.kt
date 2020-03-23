package com.vlad1m1r.bltaxi.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

internal class TrackerImpl(private val firebaseAnalytics: FirebaseAnalytics) : Tracker {

    override fun track(event: Event) {

        val dataBundle: Bundle? =
            if (event.data.isEmpty())
                null
            else
                Bundle().apply {
                    event.data.forEach { (key, value) ->
                        putString(key, value)
                    }
                }
        firebaseAnalytics.logEvent(event.name, dataBundle)
    }

    override fun enableTracking(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}
