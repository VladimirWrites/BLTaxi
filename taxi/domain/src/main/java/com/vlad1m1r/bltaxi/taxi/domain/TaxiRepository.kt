package com.vlad1m1r.bltaxi.taxi.domain

interface TaxiRepository {
    suspend fun getTaxis(): TaxisResult
    fun getItemPosition(id: Long): Int
    fun setItemPosition(id: Long, position: Int)
}