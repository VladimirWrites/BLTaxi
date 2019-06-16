package com.vlad1m1r.bltaxi.local

import android.content.SharedPreferences
import com.vlad1m1r.bltaxi.repository.Order

private const val KEY_ITEM_POSITION = "item_position"

class OrderImpl(private val sharedPreferences: SharedPreferences) : Order {

    override fun getItemPosition(id: Long): Int {
        return this.sharedPreferences.getInt(KEY_ITEM_POSITION + id.toString(), id.toInt() - 1)
    }

    override fun setItemPosition(id: Long, position: Int) {
        val editPreferences = this.sharedPreferences.edit()
        editPreferences.putInt(KEY_ITEM_POSITION + id.toString(), position)
        editPreferences.apply()
    }
}
