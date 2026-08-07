package com.vlad1m1r.bltaxi.taxi.ui

import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel

sealed interface TaxiAction {
    data object LoadTaxis : TaxiAction
    data class CallTaxi(val taxiViewModel: ItemTaxiViewModel) : TaxiAction
    data class CallTaxiOnViber(val taxiViewModel: ItemTaxiViewModel) : TaxiAction

    /** Moves the taxi at [from] to [to] and persists the new order. */
    data class MoveTaxi(val from: Int, val to: Int) : TaxiAction
}
