package com.vlad1m1r.bltaxi.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

internal class TrackerImpl(private val firebaseAnalytics: FirebaseAnalytics) : Tracker {

    override fun track(event: Event) {

        val data: Bundle? =
            if (event.dataMap.isEmpty())
                null
            else
                Bundle().apply {
                    event.dataMap.forEach { (key, value) ->
                        putString(key, value)
                    }
                }
        firebaseAnalytics.logEvent(event.name, data)
    }

    override fun enableTracking(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}
