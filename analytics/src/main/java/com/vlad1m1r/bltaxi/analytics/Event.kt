package com.vlad1m1r.bltaxi.analytics

abstract class Event {
    abstract val name: String
    abstract val data: Map<String, String>
}
