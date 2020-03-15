package com.vlad1m1r.bltaxi.taxi.adapter

interface MoveItem {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
}
