package com.vlad1m1r.bltaxi.local.database

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.Language
import org.junit.Test

class ConvertersShould {

    private val converters = Converters()

    @Test
    fun convertLanguageCodeToLanguage() {
        assertThat(converters.languageCodeToLanguage("en")).isEqualTo(Language.EN)
    }

    @Test
    fun convertLanguageToLanguageCode() {
        assertThat(converters.languageToLanguageCode(Language.BS)).isEqualTo("bs")
    }
}