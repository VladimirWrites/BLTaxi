package com.vlad1m1r.bltaxi.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.vlad1m1r.bltaxi.domain.Language
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

@Dao
interface TaxiDao {

        @Query("SELECT * FROM taxis WHERE language LIKE :language")
        fun getAll(language: Language): List<Taxi>

        @Insert
        fun insertAll(taxis: List<Taxi>)

        @Query("DELETE FROM taxis WHERE language LIKE :language")
        fun deleteAll(language: Language)

        @Transaction
        fun replaceAll(taxis: List<Taxi>, language: Language) {
                deleteAll(language)
                insertAll(taxis)
        }
}
