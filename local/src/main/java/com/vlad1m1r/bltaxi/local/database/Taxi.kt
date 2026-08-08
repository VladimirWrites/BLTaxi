package com.vlad1m1r.bltaxi.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import java.util.*

@Entity(tableName = "taxis")
data class Taxi(
    @ColumnInfo(name = "taxi_id")  val taxiId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "tariff1_start") val tariff1Start: String,
    @ColumnInfo(name = "tariff1_price_per_km") val tariff1PricePerKm: String,
    @ColumnInfo(name = "tariff1_hour_of_waiting") val tariff1HourOfWaiting: String,
    @ColumnInfo(name = "tariff2_start") val tariff2Start: String,
    @ColumnInfo(name = "tariff2_price_per_km") val tariff2PricePerKm: String,
    @ColumnInfo(name = "tariff2_hour_of_waiting") val tariff2HourOfWaiting: String,
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
    return ItemTaxi(
        id = taxiId,
        name = name,
        phoneNumber = phoneNumber,
        tariff1 = Tariff(
            start = tariff1Start,
            pricePerKm = tariff1PricePerKm,
            hourOfWaiting = tariff1HourOfWaiting
        ),
        tariff2 = Tariff(
            start = tariff2Start,
            pricePerKm = tariff2PricePerKm,
            hourOfWaiting = tariff2HourOfWaiting
        ),
        additionalInfo = additionalInfo,
        viberNumber = viberNumber
    )
}

internal fun ItemTaxi.toTaxi(language: Language): Taxi {
    return Taxi(
        taxiId = id,
        name = name,
        phoneNumber = phoneNumber,
        tariff1Start = tariff1.start,
        tariff1PricePerKm = tariff1.pricePerKm,
        tariff1HourOfWaiting = tariff1.hourOfWaiting,
        tariff2Start = tariff2.start,
        tariff2PricePerKm = tariff2.pricePerKm,
        tariff2HourOfWaiting = tariff2.hourOfWaiting,
        additionalInfo = additionalInfo,
        viberNumber = viberNumber,
        language = language
    )
}
