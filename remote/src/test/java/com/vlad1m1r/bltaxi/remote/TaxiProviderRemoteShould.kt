package com.vlad1m1r.bltaxi.remote

import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.vlad1m1r.bltaxi.taxi.domain.Language
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TaxiProviderRemoteShould {

    private val taxiService = mock<TaxiService>()
    private val taxiProviderRemote: TaxiProviderRemote = TaxiProviderRemoteImpl(taxiService)

    private val taxi = Taxi(
        10,
        "name",
        "number",
        "start",
        "price",
        "additional",
        "viber"
    )

    @Test
    fun getTaxis_withCorrectLanguage() {
        runBlocking {
            whenever(taxiService.taxis(any())).thenReturn(emptyList())

            taxiProviderRemote.getTaxis(Language.SR)

            verify(taxiService).taxis(Language.SR.code)
        }
    }

    @Test
    fun mapTaxis_inOrder() {
        runBlocking {
            val taxis = listOf(taxi.copy(id = 20), taxi.copy(id = 30))
            whenever(taxiService.taxis(any())).thenReturn(taxis)

            val itemTaxiList = taxiProviderRemote.getTaxis(Language.SR)
            val expected = listOf(taxis[0].toItemTaxi(), taxis[1].toItemTaxi())

            assertThat(itemTaxiList).containsExactlyElementsIn(expected).inOrder()
        }
    }
}
