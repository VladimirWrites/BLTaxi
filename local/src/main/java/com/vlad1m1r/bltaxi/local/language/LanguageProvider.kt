package com.vlad1m1r.bltaxi.local.language

import com.vlad1m1r.bltaxi.taxi.domain.Language

interface LanguageProvider {
    fun getLanguage(): Language
}
