package com.vlad1m1r.bltaxi.repository

import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

interface TaxiProviderRemote {
    suspend fun getTaxis(language: Language): List<ItemTaxi>
}
