package com.vlad1m1r.bltaxi.taxi.data

import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.order.OrderProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import java.lang.Exception

internal class TaxiRepositoryImpl(
    private val orderProvider: OrderProvider,
    private val taxiProviderLocal: TaxiProviderLocal,
    private val taxiProviderRemote: TaxiProviderRemote,
    private val languageProvider: LanguageProvider
) : TaxiRepository {

    override suspend fun getTaxis(): TaxisResult {
        val currentLanguage = languageProvider.getLanguage()
        val localTaxis = taxiProviderLocal.getTaxis(currentLanguage)

        return if (localTaxis.isNotEmpty()) {
            TaxisResult.Success(localTaxis)
        } else {
            try {
                fetchRemoteData(currentLanguage)
            } catch (e: Exception) {
                TaxisResult.Error
            }
        }
    }

    private suspend fun fetchRemoteData(currentLanguage: Language): TaxisResult {
        val remoteTaxis = taxiProviderRemote.getTaxis(currentLanguage)
        taxiProviderLocal.saveTaxis(remoteTaxis, currentLanguage)
        return TaxisResult.Success(remoteTaxis)
    }

    override fun getItemPosition(id: Long): Int {
        return orderProvider.getItemPosition(id)
    }

    override fun setItemPosition(id: Long, position: Int) {
        orderProvider.setItemPosition(id, position)
    }
}
