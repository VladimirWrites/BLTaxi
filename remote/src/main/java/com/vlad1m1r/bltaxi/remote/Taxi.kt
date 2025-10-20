package com.vlad1m1r.bltaxi.remote

import androidx.annotation.Keep
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff as DomainTariff

@Keep
data class Taxi(
    val id: Long,
    val name: String,
    val number: String,
    val tariff1: Tariff,
    val tariff2: Tariff,
    val additional: String?,
    val viber: String?
) {
    fun toItemTaxi(): ItemTaxi {
        return ItemTaxi(
            id = id,
            name = name,
            phoneNumber = number,
            tariff1 = DomainTariff(
                start = tariff1.start,
                pricePerKm = tariff1.pricePerKm,
                hourOfWaiting = tariff1.hourOfWaiting
            ),
            tariff2 = DomainTariff(
                start = tariff2.start,
                pricePerKm = tariff2.pricePerKm,
                hourOfWaiting = tariff2.hourOfWaiting
            ),
            additionalInfo = additional,
            viberNumber = viber
        )
    }
}
