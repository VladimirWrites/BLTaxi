package com.vlad1m1r.bltaxi.local.database

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.Language
import org.junit.Test
import java.util.*

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

    @Test
    fun convertTimestampToDate() {
        val calendar = Calendar.getInstance()
        assertThat(converters.toDate(calendar.timeInMillis)).isEqualTo(calendar.time)
    }

    @Test
    fun convertNullTimestampToNullDate() {
        assertThat(converters.toDate(null)).isNull()
    }

    @Test
    fun convertDateToTimestamp() {
        val calendar = Calendar.getInstance()
        assertThat(converters.toTimestamp(calendar.time)).isEqualTo(calendar.timeInMillis)
    }

    @Test
    fun convertNullDateToNullTimestamp() {
        val calendar = Calendar.getInstance()
        assertThat(converters.toTimestamp(null)).isNull()
    }
}