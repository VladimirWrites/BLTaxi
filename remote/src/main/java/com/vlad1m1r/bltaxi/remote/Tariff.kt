package com.vlad1m1r.bltaxi.remote

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Tariff(
    @SerialName("start") val start: String,
    @SerialName("price_per_km") val pricePerKm: String,
    @SerialName("hour_of_waiting") val hourOfWaiting: String
)
