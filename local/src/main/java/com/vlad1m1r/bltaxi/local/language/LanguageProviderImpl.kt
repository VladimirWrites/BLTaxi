package com.vlad1m1r.bltaxi.local.language

import android.content.Context
import android.os.Build
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.Language.Companion.fromCode
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

class LanguageProviderImpl(
    @ApplicationContext private val context: Context
) : LanguageProvider {

    override fun getLanguage(): Language {
        val defaultLanguage = context.getLocale().language
        return fromCode(defaultLanguage)
    }
}

@Suppress("DEPRECATION")
private fun Context.getLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0)
    } else {
        resources.configuration.locale
    }
}
