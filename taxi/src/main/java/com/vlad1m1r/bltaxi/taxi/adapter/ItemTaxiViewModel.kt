package com.vlad1m1r.bltaxi.taxi.adapter

import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

class ItemTaxiViewModel(
    val itemTaxi: ItemTaxi,
    private val call: (itemTaxi: ItemTaxi) -> Unit,
    private val callViber: (itemTaxi: ItemTaxi) -> Unit
) {
    val isViberVisible: Boolean = !itemTaxi.viberNumber.isNullOrBlank()

    fun callTaxi() = call(itemTaxi)
    fun callTaxiOnViber() = callViber(itemTaxi)
}
