package com.vlad1m1r.bltaxi.taxi.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TaxiRepositoryShould {
    private val orderProvider = mock<OrderProvider>()
    private val taxiProviderLocal = mock<TaxiProviderLocal>()
    private val taxiProviderRemote = mock<TaxiProviderRemote>()
    private val languageProvider = mock<LanguageProvider>()

    private val taxiRepository: TaxiRepository = TaxiRepositoryImpl(
        orderProvider, taxiProviderLocal, taxiProviderRemote, languageProvider
    )

    private val itemTaxi = ItemTaxi(
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    @Test
    fun getTaxisFromLocal_whenLocalIsNotEmpty() {
        runBlocking {
            val listOfTaxis = listOf(itemTaxi)
            whenever(taxiProviderLocal.getTaxis(any())).thenReturn(listOfTaxis)
            whenever(languageProvider.getLanguage()).thenReturn(Language.BS)

            assertThat(taxiRepository.getTaxis()).isEqualTo(TaxisResult.Success(listOfTaxis))
        }
    }

    @Test
    fun getTaxisFromRemote_whenLocalIsEmpty() {
        runBlocking {
            val listOfTaxis = listOf(itemTaxi)
            whenever(taxiProviderLocal.getTaxis(any())).thenReturn(emptyList())
            whenever(taxiProviderRemote.getTaxis(any())).thenReturn(listOfTaxis)
            whenever(languageProvider.getLanguage()).thenReturn(Language.BS)

            assertThat(taxiRepository.getTaxis()).isEqualTo(TaxisResult.Success(listOfTaxis))
        }
    }

    @Test
    fun saveDataToLocal_whenFetchedFromRemote() {
        runBlocking {
            val listOfTaxis = listOf(itemTaxi)
            whenever(taxiProviderLocal.getTaxis(any())).thenReturn(emptyList())
            whenever(taxiProviderRemote.getTaxis(any())).thenReturn(listOfTaxis)
            whenever(languageProvider.getLanguage()).thenReturn(Language.EN)

            taxiRepository.getTaxis()

            verify(taxiProviderLocal).saveTaxis(listOfTaxis, Language.EN)
        }
    }

    @Test
    fun getFromLocal_withCorrectLanguage() {
        runBlocking {
            val listOfTaxis = listOf(itemTaxi)
            whenever(taxiProviderLocal.getTaxis(any())).thenReturn(listOfTaxis)
            whenever(languageProvider.getLanguage()).thenReturn(Language.HR)

            taxiRepository.getTaxis()

            verify(taxiProviderLocal).getTaxis(Language.HR)
        }
    }

    @Test
    fun getFromRemote_withCorrectLanguage() {
        runBlocking {
            val listOfTaxis = listOf(itemTaxi)
            whenever(taxiProviderLocal.getTaxis(any())).thenReturn(emptyList())
            whenever(taxiProviderRemote.getTaxis(any())).thenReturn(listOfTaxis)
            whenever(languageProvider.getLanguage()).thenReturn(Language.SR)

            taxiRepository.getTaxis()

            verify(taxiProviderRemote).getTaxis(Language.SR)
        }
    }

    @Test
    fun getItemPosition_fromOrderProvider() {
        whenever(orderProvider.getItemPosition(10)).thenReturn(20)

        assertThat(taxiRepository.getItemPosition(10)).isEqualTo(20)
    }

    @Test
    fun setItemPosition_toOrderProvider() {
        taxiRepository.setItemPosition(10, 20)

        verify(orderProvider).setItemPosition(10, 20)
    }
}