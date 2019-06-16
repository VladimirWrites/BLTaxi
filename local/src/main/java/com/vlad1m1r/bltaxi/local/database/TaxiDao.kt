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

        @Query("SELECT * FROM taxis")
        fun getAll(): List<Taxi>

        @Insert
        fun insertAll(taxis: List<Taxi>)

        @Query("DELETE FROM taxis")
        fun deleteAll()

        @Transaction
        fun replaceAll(taxis: List<ItemTaxi>, language: Language) {
                deleteAll()
                insertAll(taxis.map { it.toTaxi(language) })
        }

        @Transaction
        fun isEmpty():Boolean {
                return getAll().isNullOrEmpty()
        }

        @Transaction
        fun getLanguage(): Language? {
                if(getAll().isNullOrEmpty()) return null
                return getAll()[0].language
        }
}
