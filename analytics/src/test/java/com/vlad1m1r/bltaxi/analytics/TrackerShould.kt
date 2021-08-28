package com.vlad1m1r.bltaxi.analytics

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.analytics.FirebaseAnalytics
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class TrackerShould {

    private val firebaseAnalytics = mock<FirebaseAnalytics>()
    private val context = mock<Context>()
    private val sharedPreferences = mock<SharedPreferences>()
    private val tracker: Tracker = TrackerImpl(firebaseAnalytics, context, sharedPreferences)

    @Test
    fun trackEventWithFirebaseAnalytics() {
        val eventName = "event_name"
        val event = TestEvent(eventName, emptyMap())

        tracker.track(event)

        verify(firebaseAnalytics).logEvent(eventName, null)
    }

    @Test
    fun enableTracking() {
        tracker.enableTracking(true)

        verify(firebaseAnalytics).setAnalyticsCollectionEnabled(true)
    }

    @Test
    fun disableTracking() {
        tracker.enableTracking(false)

        verify(firebaseAnalytics).setAnalyticsCollectionEnabled(false)
    }

    class TestEvent(override val name: String, override val data: Map<String, String>): Event()
}
