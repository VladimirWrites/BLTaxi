package com.vlad1m1r.bltaxi.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Taxi::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaxiDatabase : RoomDatabase() {
    abstract fun taxiDao(): TaxiDao
}
