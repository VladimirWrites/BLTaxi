package com.vlad1m1r.bltaxi.remote

import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi

interface TaxiProviderRemote {
    suspend fun getTaxis(language: Language): List<ItemTaxi>
}
