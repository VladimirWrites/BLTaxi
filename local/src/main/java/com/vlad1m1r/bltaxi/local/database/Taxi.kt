package com.vlad1m1r.bltaxi.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import java.util.*

@Entity(tableName = "taxis")
data class Taxi(
    @ColumnInfo(name = "taxi_id")  val taxiId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "start_price") val startPrice: String,
    @ColumnInfo(name = "price_per_km") val pricePerKm: String,
    @ColumnInfo(name = "additional_info") val additionalInfo: String?,
    @ColumnInfo(name = "viber_number") val viberNumber: String?,
    @ColumnInfo(name = "language") val language: Language
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "time_of_update")
    var timestamp: Date = Calendar.getInstance().time
}

internal fun Taxi.toItemTaxi(): ItemTaxi {
    return ItemTaxi(taxiId, name, phoneNumber, startPrice, pricePerKm, additionalInfo, viberNumber)
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
        language
    )
}
