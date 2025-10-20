package com.vlad1m1r.bltaxi.taxi.domain.model

data class ItemTaxi(
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val tariff1: Tariff,
    val tariff2: Tariff,
    val additionalInfo: String? = null,
    val viberNumber: String? = null
) {
    // Backwards compatibility properties
    val startPrice: String get() = tariff1.start
    val pricePerKm: String get() = tariff1.pricePerKm
}
