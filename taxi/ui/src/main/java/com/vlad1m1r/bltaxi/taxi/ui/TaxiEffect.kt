package com.vlad1m1r.bltaxi.taxi.ui

sealed interface TaxiEffect {
    data class ShowError(val message: String) : TaxiEffect
}
