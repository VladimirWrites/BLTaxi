package com.vlad1m1r.bltaxi.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class TrackerShould {

    val firebaseAnalytics = mock<FirebaseAnalytics>()
    val tracker: Tracker = TrackerImpl(firebaseAnalytics)

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

    open class TestEvent(override val name: String, override val dataMap: Map<String, String>): Event()
}
