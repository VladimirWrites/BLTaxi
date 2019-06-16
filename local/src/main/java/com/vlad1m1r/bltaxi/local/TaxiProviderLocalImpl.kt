package com.vlad1m1r.bltaxi.local

import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.local.database.Taxi
import com.vlad1m1r.bltaxi.local.database.TaxiDatabase
import com.vlad1m1r.bltaxi.local.database.toItemTaxi
import com.vlad1m1r.bltaxi.repository.TaxiProviderLocal

const val CACHE_TIME_IN_MILLIS = 86_400_000

class TaxiProviderLocalImpl(private val taxiDatabase: TaxiDatabase) : TaxiProviderLocal {
    override suspend fun getTaxis(language: Language): List<ItemTaxi> {
        val taxis = taxiDatabase.taxiDao().getAll(language)
        return if(taxis.isNullOrEmpty())
            emptyList()
        else
            taxiDatabase.taxiDao().getAll(language).map { it.toItemTaxi() }
    }

    override suspend fun saveTaxis(taxis: List<ItemTaxi>, language: Language) {
        taxiDatabase.taxiDao().replaceAll(taxis, language)
    }

    override suspend fun isCacheOld(language: Language): Boolean {
        val taxis = taxiDatabase.taxiDao().getAll(language)
        return if(taxis.isNullOrEmpty()) true
        else {
            System.currentTimeMillis() - taxis[0].timestamp > CACHE_TIME_IN_MILLIS
        }
    }
}