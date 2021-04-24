package com.vlad1m1r.bltaxi.shortcuts.di

import android.content.Context
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShortcutManagerModule {

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    @Provides
    fun provideShortcutManager(
        @ApplicationContext context: Context
    ): ShortcutManager {
        return context.getSystemService(ShortcutManager::class.java)!!
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ShortcutsModule {

    @Binds
    abstract fun bindShortcutHandler(
        shortcutHandlerImpl: ShortcutHandlerImpl
    ): ShortcutHandler
}
