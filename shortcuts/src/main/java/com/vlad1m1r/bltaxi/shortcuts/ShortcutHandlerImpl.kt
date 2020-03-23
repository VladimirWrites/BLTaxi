package com.vlad1m1r.bltaxi.shortcuts

import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import kotlin.math.min

internal class ShortcutHandlerImpl(
    private val shortcutManager: ShortcutManager,
    private val shortcutInfoProvider: ShortcutInfoProvider
) : ShortcutHandler {

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun addShortcutsForTaxis(items: List<ItemTaxi>) {
        if (items.isNotEmpty()) {

            shortcutManager.removeAllDynamicShortcuts()

            val shortcuts = ArrayList<ShortcutInfo>()

            for (i in 0 until min(3, items.size)) {
                val shortcut = shortcutInfoProvider.getShortcutInfoFromItemTaxi(items[i])
                shortcuts.add(shortcut)
            }

            shortcutManager.dynamicShortcuts = shortcuts

        }
    }
}
