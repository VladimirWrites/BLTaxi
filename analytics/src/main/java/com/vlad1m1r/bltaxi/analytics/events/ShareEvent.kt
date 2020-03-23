package com.vlad1m1r.bltaxi.analytics.events

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE
import com.google.firebase.analytics.FirebaseAnalytics.Param.METHOD
import com.vlad1m1r.bltaxi.analytics.Event

class ShareAppEvent : Event() {
    override val name: String = FirebaseAnalytics.Event.SHARE
    override val dataMap = mapOf(
        CONTENT_TYPE to "App",
        METHOD to "About Screen"
    )

}
