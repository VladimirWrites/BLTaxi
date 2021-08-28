package com.vlad1m1r.bltaxi.sync

import com.vlad1m1r.bltaxi.local.language.LanguageProvider
import com.vlad1m1r.bltaxi.local.taxi.TaxiProviderLocal
import com.vlad1m1r.bltaxi.remote.TaxiProviderRemote
import javax.inject.Inject

class SyncTaxis @Inject constructor(
    private val languageProvider: LanguageProvider,
    private val remote: TaxiProviderRemote,
    private val local: TaxiProviderLocal
) {
    suspend operator fun invoke() {
        val language = languageProvider.getLanguage()
        val remoteTaxis = remote.getTaxis(language)
        local.saveTaxis(remoteTaxis, language)
    }
}
