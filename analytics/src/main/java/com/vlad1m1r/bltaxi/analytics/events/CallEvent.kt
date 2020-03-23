package com.vlad1m1r.bltaxi.analytics.events

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.vlad1m1r.bltaxi.analytics.Event

class CallEvent(id: Long, taxiName: String, callType: CallVariant) : Event() {

    override val name: String = FirebaseAnalytics.Event.SELECT_CONTENT

    override val data = mapOf(
        ITEM_ID to id.toString(),
        ITEM_NAME to taxiName,
        ITEM_VARIANT to callType.variantName
    )

    enum class CallVariant(val variantName: String) {
        CALL("Call"),
        VIBER("Viber")
    }
}
