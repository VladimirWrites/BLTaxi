package com.vlad1m1r.bltaxi.local.taxi

import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.local.database.TaxiDatabase
import com.vlad1m1r.bltaxi.local.database.toItemTaxi
import com.vlad1m1r.bltaxi.local.database.toTaxi

internal class TaxiProviderLocalImpl(private val taxiDatabase: TaxiDatabase) :
    TaxiProviderLocal {
    override suspend fun getTaxis(language: Language): List<ItemTaxi> {
        val taxis = taxiDatabase.taxiDao().getAll(language)
        return if(taxis.isNullOrEmpty())
            emptyList()
        else
            taxiDatabase.taxiDao().getAll(language).map { it.toItemTaxi() }
    }

    override suspend fun saveTaxis(taxis: List<ItemTaxi>, language: Language) {
        taxiDatabase.taxiDao().replaceAll(taxis.map { it.toTaxi(language) }, language)
    }
}
