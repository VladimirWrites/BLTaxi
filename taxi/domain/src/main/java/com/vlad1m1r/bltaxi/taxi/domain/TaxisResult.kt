package com.vlad1m1r.bltaxi.taxi.domain

import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi

sealed class TaxisResult {
    data class Success(val list: List<ItemTaxi>) : TaxisResult()
    object Error : TaxisResult()
}