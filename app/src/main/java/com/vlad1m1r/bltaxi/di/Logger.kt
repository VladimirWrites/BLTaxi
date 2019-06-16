package com.vlad1m1r.bltaxi.di

import android.util.Log
import com.vlad1m1r.basedata.exhaustive
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class Logger: Logger() {
    override fun log(level: Level, msg: MESSAGE) {
        when(level) {
            Level.DEBUG -> Log.d("KOINd", msg)
            Level.ERROR -> Log.e("KOINe", msg)
            Level.INFO -> Log.i("KOINi", msg)
            Level.NONE -> Log.v("KOINn", msg)
        }.exhaustive
    }

}