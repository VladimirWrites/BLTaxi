package com.vlad1m1r.bltaxi.local.order

import android.content.SharedPreferences
import javax.inject.Inject

private const val KEY_ITEM_POSITION = "item_position"

class OrderProviderImpl(
    private val sharedPreferences: SharedPreferences
) : OrderProvider {

    override fun getItemPosition(id: Long): Int {
        return this.sharedPreferences.getInt(KEY_ITEM_POSITION + id.toString(), -1)
    }

    override fun setItemPosition(id: Long, position: Int) {
        val editPreferences = this.sharedPreferences.edit()
        editPreferences.putInt(KEY_ITEM_POSITION + id.toString(), position)
        editPreferences.apply()
    }
}
