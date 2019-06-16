package com.vlad1m1r.bltaxi.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

@Entity(tableName = "taxis")
data class Taxi(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "start_price") val startPrice: String,
    @ColumnInfo(name = "price_per_km") val pricePerKm: String,
    @ColumnInfo(name = "additional_info") val additionalInfo: String?,
    @ColumnInfo(name = "viber_number") val viberNumber: String?,
    @ColumnInfo(name = "time_of_update") val timestamp: Long,
    @ColumnInfo(name = "language") val language: Language
)

internal fun Taxi.toItemTaxi(): ItemTaxi {
    return ItemTaxi(id, name, phoneNumber, startPrice, pricePerKm, additionalInfo, viberNumber)
}

internal fun ItemTaxi.toTaxi(language: Language): Taxi {
    return Taxi(
        id,
        name,
        phoneNumber,
        startPrice,
        pricePerKm,
        additionalInfo,
        viberNumber,
        System.currentTimeMillis(),
        language
    )
}