package com.vlad1m1r.bltaxi.repository

import com.vlad1m1r.bltaxi.domain.Language

interface LanguageProvider {
    fun getLanguage(): Language
}
