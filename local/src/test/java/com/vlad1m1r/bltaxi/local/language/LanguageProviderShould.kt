package com.vlad1m1r.bltaxi.local.language

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.vlad1m1r.bltaxi.taxi.domain.Language
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier

import java.util.*

class LanguageProviderShould {
    private val locale = mock<Locale> {
        on { language }.thenReturn("en")
    }
    private val locales = mock<LocaleList> {
        on { get(0) }.thenReturn(locale)
    }
    private val configuration = mock<Configuration> {
        on { locales }.thenReturn(locales)
    }
    private val resources = mock<Resources> {
        on { configuration }.thenReturn(configuration)
    }
    private val context = mock<Context> {
        on { resources }.thenReturn(resources)
    }

    private val languageProvider: LanguageProvider = LanguageProviderImpl(context)

    @Test
    fun getLanguage() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), Build.VERSION_CODES.N)

        val language = languageProvider.getLanguage()

        assertThat(language).isEqualTo(Language.EN)
    }

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any) {
        field.isAccessible = true

        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

        field.set(null, newValue)
    }
}