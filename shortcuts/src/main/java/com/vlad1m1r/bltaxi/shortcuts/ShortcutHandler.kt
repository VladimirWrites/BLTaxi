package com.vlad1m1r.bltaxi.shortcuts

import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi

interface ShortcutHandler {
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun addShortcutsForTaxis(items: List<ItemTaxi>)
}
