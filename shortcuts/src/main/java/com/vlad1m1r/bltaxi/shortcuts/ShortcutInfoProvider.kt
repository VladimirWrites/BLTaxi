package com.vlad1m1r.bltaxi.shortcuts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class ShortcutInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun getShortcutInfoFromItemTaxi(itemTaxi: ItemTaxi): ShortcutInfo {
        val uri = "tel:${itemTaxi.phoneNumber}"
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)

        return ShortcutInfo.Builder(context, itemTaxi.id.toString())
            .setShortLabel(itemTaxi.name)
            .setLongLabel(itemTaxi.name)
            .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_call))
            .setIntent(intent)
            .build()
    }
}
