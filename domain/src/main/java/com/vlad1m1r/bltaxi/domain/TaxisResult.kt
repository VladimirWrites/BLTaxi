package com.vlad1m1r.bltaxi.domain

import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

sealed class TaxisResult {
    data class Success(val list: List<ItemTaxi>) : TaxisResult()
    object Error : TaxisResult()
}