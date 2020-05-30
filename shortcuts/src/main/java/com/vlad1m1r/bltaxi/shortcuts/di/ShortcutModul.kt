package com.vlad1m1r.bltaxi.shortcuts.di

import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandlerImpl
import com.vlad1m1r.bltaxi.shortcuts.ShortcutInfoProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.N_MR1)
val shortcutsModule = module {
    single {
        androidContext().getSystemService(ShortcutManager::class.java)!!
    }
    single { ShortcutInfoProvider(androidContext()) }
    single<ShortcutHandler> { ShortcutHandlerImpl(get(), get()) }
}
