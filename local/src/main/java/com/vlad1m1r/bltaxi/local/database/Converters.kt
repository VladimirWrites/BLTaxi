package com.vlad1m1r.bltaxi.local.database

import androidx.room.TypeConverter
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.Language.Companion.fromCode

internal class Converters {
    @TypeConverter
    fun languageToLanguageCode(language: Language): String {
        return language.code
    }

    @TypeConverter
    fun languageCodeToLanguage(languageCode: String): Language {
        return fromCode(languageCode)
    }
}
