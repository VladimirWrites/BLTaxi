package com.vlad1m1r.bltaxi.shortcuts.di

import android.content.Context
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandlerImpl
import com.vlad1m1r.bltaxi.shortcuts.ShortcutInfoProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShortcutsModule {

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    @Provides
    fun provideShortcutHandler(
        @ApplicationContext context: Context,
        shortcutInfoProvider: ShortcutInfoProvider
    ): ShortcutHandler {
        return ShortcutHandlerImpl(
            context.getSystemService(ShortcutManager::class.java)!!,
            shortcutInfoProvider
        )
    }
}
