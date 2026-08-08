package com.vlad1m1r.bltaxi.taxi.ui

import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel

data class TaxiState(
    val taxis: List<ItemTaxiViewModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
