package com.vlad1m1r.bltaxi.about.ui

sealed interface AboutEffect {
    data class ShowError(val message: String) : AboutEffect
}
