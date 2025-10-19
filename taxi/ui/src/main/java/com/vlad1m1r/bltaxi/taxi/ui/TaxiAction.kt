package com.vlad1m1r.bltaxi.taxi.ui

import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel

sealed interface TaxiAction {
    object LoadTaxis : TaxiAction
    data class CallTaxi(val taxiViewModel: ItemTaxiViewModel) : TaxiAction
    data class CallTaxiOnViber(val taxiViewModel: ItemTaxiViewModel) : TaxiAction
    data class ReorderTaxis(val taxis: List<ItemTaxiViewModel>) : TaxiAction
}
