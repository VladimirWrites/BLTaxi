package com.vlad1m1r.bltaxi.local.language

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.taxi.domain.Language
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale

/**
 * Robolectric supplies a real [Build.VERSION.SDK_INT] per `@Config(sdk = ...)`, which is how both
 * branches of the locale lookup get covered without reflecting over final fields (impossible on
 * JDK 17).
 */
@RunWith(RobolectricTestRunner::class)
class LanguageProviderShould {

    private val configuration = Configuration().apply { setLocale(Locale.ENGLISH) }
    private val resources = mock<Resources> {
        on { configuration }.thenReturn(configuration)
    }
    private val context = mock<Context> {
        on { resources }.thenReturn(resources)
    }

    private val languageProvider: LanguageProvider = LanguageProviderImpl(context)

    @Test
    @Config(sdk = [29])
    fun getLanguage_fromLocaleList() {
        assertThat(languageProvider.getLanguage()).isEqualTo(Language.EN)
    }

    @Test
    @Config(sdk = [23])
    fun getLanguage_fromDeprecatedLocale() {
        assertThat(languageProvider.getLanguage()).isEqualTo(Language.EN)
    }
}
