package com.vlad1m1r.bltaxi.analytics.events

import com.google.common.truth.Truth.assertThat
import com.google.firebase.analytics.FirebaseAnalytics.Event.*
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.vlad1m1r.bltaxi.analytics.events.CallEvent.CallVariant.*
import org.junit.Test

class CallEventShould {

    private val callEvent = CallEvent(10, "name", CALL)

    @Test
    fun getName() {
        assertThat(callEvent.name).isEqualTo(SELECT_CONTENT)
    }

    @Test
    fun getData() {
        assertThat(callEvent.data).containsExactlyEntriesIn(
            mapOf(
                ITEM_ID to 10.toString(),
                ITEM_NAME to "name",
                ITEM_VARIANT to CALL.variantName
            )
        )
    }
}