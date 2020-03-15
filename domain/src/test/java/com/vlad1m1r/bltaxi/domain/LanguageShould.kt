package com.vlad1m1r.bltaxi.domain

import org.junit.Test

import com.google.common.truth.Truth.assertThat

class LanguageShould {

    @Test
    fun getEnglishLanguage_fromEnglishCode() {
        assertThat(Language.fromCode("en")).isEqualTo(Language.EN)
    }

    @Test
    fun getBosnianLanguage_fromBosnianCode() {
        assertThat(Language.fromCode("bs")).isEqualTo(Language.BS)
    }

    @Test
    fun getCroatianLanguage_fromCroatianCode() {
        assertThat(Language.fromCode("hr")).isEqualTo(Language.HR)
    }

    @Test
    fun getSerbianLanguage_fromSerbianCode() {
        assertThat(Language.fromCode("sr")).isEqualTo(Language.SR)
    }
}
