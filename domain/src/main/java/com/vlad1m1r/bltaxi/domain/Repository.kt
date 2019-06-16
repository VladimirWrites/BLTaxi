package com.vlad1m1r.bltaxi.domain

interface Repository {
    suspend fun getTaxis(): TaxisResult
    fun getItemPosition(id: Long): Int
    fun setItemPosition(id: Long, position: Int)
}