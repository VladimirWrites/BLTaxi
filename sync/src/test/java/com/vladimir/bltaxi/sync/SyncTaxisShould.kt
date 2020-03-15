package com.vladimir.bltaxi.sync

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SyncTaxisShould {

    private val languageProvider = mock<LanguageProvider>()
    private val taxiProviderRemote = mock<TaxiProviderRemote>()
    private val taxiProviderLocal = mock<TaxiProviderLocal>()

    private val syncTaxis = SyncTaxis(languageProvider, taxiProviderRemote, taxiProviderLocal)

    @Test
    fun requestRemoteTaxis_withCorrectLanguage() {
        runBlocking {
            val language = Language.EN
            whenever(languageProvider.getLanguage()).thenReturn(language)

            syncTaxis()

            verify(taxiProviderRemote).getTaxis(language)
        }
    }

    @Test
    fun saveTaxisToLocal() {
        runBlocking {
            val language = Language.EN
            val taxis = listOf(
                ItemTaxi(
                    0,
                    "name",
                    "phone_number",
                    "start_price",
                    "price_per_km",
                    "additional_info",
                    "viber_number"
                )
            )
            whenever(languageProvider.getLanguage()).thenReturn(language)
            whenever(taxiProviderRemote.getTaxis(language)).thenReturn(taxis)

            syncTaxis()

            verify(taxiProviderLocal).saveTaxis(taxis, language)
        }
    }
}
