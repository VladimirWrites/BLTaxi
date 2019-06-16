package com.vlad1m1r.bltaxi.analytics.events

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.vlad1m1r.bltaxi.analytics.Event

class CallEvent(id: Long, taxiName: String, callType: CallVariant) : Event() {

    override val name: String = FirebaseAnalytics.Event.SELECT_CONTENT

    override val data: Bundle? = Bundle().apply {
        putString(ITEM_ID, id.toString())
        putString(ITEM_NAME, taxiName)
        putString(ITEM_VARIANT, callType.variantName)
    }

    enum class CallVariant(val variantName: String) {
        CALL("Call"),
        VIBER("Viber")
    }
}