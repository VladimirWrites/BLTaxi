package com.vlad1m1r.bltaxi.sync

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
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
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )
            )
            whenever(languageProvider.getLanguage()).thenReturn(language)
            whenever(taxiProviderRemote.getTaxis(language)).thenReturn(taxis)

            syncTaxis()

            verify(taxiProviderLocal).saveTaxis(taxis, language)
        }
    }
}
