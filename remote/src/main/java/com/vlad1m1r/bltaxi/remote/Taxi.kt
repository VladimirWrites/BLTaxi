package com.vlad1m1r.bltaxi.remote

import androidx.annotation.Keep
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

@Keep
data class Taxi(
    val id: Long,
    val name: String,
    val number: String,
    val start: String,
    val price: String,
    val additional: String?,
    val viber: String?
) {
    fun toItemTaxi(): ItemTaxi {
        return ItemTaxi(id, name, number, start, price, additional, viber)
    }
}
