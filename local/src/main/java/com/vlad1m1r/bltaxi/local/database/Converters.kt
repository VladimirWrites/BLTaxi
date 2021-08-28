package com.vlad1m1r.bltaxi.local.database

import androidx.room.TypeConverter
import com.vlad1m1r.bltaxi.taxi.domain.Language
import com.vlad1m1r.bltaxi.taxi.domain.Language.Companion.fromCode
import java.util.*

internal class Converters {
    @TypeConverter
    fun languageToLanguageCode(language: Language): String {
        return language.code
    }

    @TypeConverter
    fun languageCodeToLanguage(languageCode: String): Language {
        return fromCode(languageCode)
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
