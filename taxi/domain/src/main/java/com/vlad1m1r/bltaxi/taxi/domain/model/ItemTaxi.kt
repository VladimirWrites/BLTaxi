package com.vlad1m1r.bltaxi.taxi.domain.model

data class ItemTaxi(
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val startPrice: String,
    val pricePerKm: String,
    val additionalInfo: String? = null,
    val viberNumber: String? = null
)
