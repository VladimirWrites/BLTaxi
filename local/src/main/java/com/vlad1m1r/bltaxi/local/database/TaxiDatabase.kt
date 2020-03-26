package com.vlad1m1r.bltaxi.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Taxi::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaxiDatabase : RoomDatabase() {
    abstract fun taxiDao(): TaxiDao
}

internal fun provideTaxiDatabase(context: Context): TaxiDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        TaxiDatabase::class.java, "bltaxi-database"
    ).build()
}
