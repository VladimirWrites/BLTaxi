package com.vlad1m1r.bltaxi.local.order

interface OrderProvider {
    fun getItemPosition(id: Long): Int
    fun setItemPosition(id: Long, position: Int)
}
