package com.vlad1m1r.bltaxi.analytics.events

import com.google.common.truth.Truth.assertThat
import com.google.firebase.analytics.FirebaseAnalytics.Event.*
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import org.junit.Test

class ShareAppEventShould {

    private val shareEvent = ShareAppEvent()

    @Test
    fun getName() {
        assertThat(shareEvent.name).isEqualTo(SHARE)
    }

    @Test
    fun getData() {
        assertThat(shareEvent.data).containsExactlyEntriesIn(
            mapOf(
                CONTENT_TYPE to "App",
                METHOD to "About Screen"
            )
        )
    }
}