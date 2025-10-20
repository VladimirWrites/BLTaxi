package com.vlad1m1r.bltaxi.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Tariff(
    @SerializedName("start") val start: String,
    @SerializedName("price_per_km") val pricePerKm: String,
    @SerializedName("hour_of_waiting") val hourOfWaiting: String
)
