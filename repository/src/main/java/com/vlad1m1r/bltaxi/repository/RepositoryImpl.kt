package com.vlad1m1r.bltaxi.repository

import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.Repository
import com.vlad1m1r.bltaxi.domain.TaxisResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

internal class RepositoryImpl(
    private val order: Order,
    private val taxiProviderLocal: TaxiProviderLocal,
    private val taxiProviderRemote: TaxiProviderRemote,
    private val languageProvider: LanguageProvider
) : Repository {

    override suspend fun getTaxis(): TaxisResult {
        val currentLanguage = languageProvider.getLanguage()
        val localTaxis = taxiProviderLocal.getTaxis(currentLanguage)


        val result: TaxisResult = if (localTaxis.isNotEmpty()) {
            TaxisResult.Success(localTaxis)
        } else {
            try {
                fetchRemoteData(currentLanguage)
            } catch (e: Exception) {
                TaxisResult.Error
            }
        }

        withContext(Dispatchers.IO) {
            refreshCache(currentLanguage)
        }

        return result
    }

    private suspend fun refreshCache(currentLanguage: Language) {
        if(taxiProviderLocal.isCacheOld(currentLanguage)) {
            try {
                fetchRemoteData(currentLanguage)
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun fetchRemoteData(currentLanguage: Language): TaxisResult {
        val remoteTaxis = taxiProviderRemote.getTaxis(currentLanguage)
        taxiProviderLocal.saveTaxis(remoteTaxis, currentLanguage)
        return TaxisResult.Success(remoteTaxis)
    }

    override fun getItemPosition(id: Long): Int {
        return order.getItemPosition(id)
    }

    override fun setItemPosition(id: Long, position: Int) {
        order.setItemPosition(id, position)
    }
}