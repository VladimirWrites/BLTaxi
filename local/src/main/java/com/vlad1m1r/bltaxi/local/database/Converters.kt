package com.vlad1m1r.bltaxi.local.database

import androidx.room.TypeConverter
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.fromCode

class Converters {
    @TypeConverter
    fun fromLanguage(language: Language): String {
        return language.code
    }

    @TypeConverter
    fun languageToString(languageCode: String): Language {
        return fromCode(languageCode)
    }
}