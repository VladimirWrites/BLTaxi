package com.vlad1m1r.bltaxi.analytics

import android.os.Bundle

abstract class Event {
    abstract val name: String
    abstract val data: Bundle?
}
