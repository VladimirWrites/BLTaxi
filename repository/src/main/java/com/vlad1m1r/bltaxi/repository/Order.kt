package com.vlad1m1r.bltaxi.repository

interface Order {
    fun getItemPosition(id: Long): Int
    fun setItemPosition(id: Long, position: Int)
}
