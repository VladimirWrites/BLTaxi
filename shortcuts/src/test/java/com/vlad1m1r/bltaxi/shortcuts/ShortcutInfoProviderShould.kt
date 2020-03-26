package com.vlad1m1r.bltaxi.shortcuts

import android.content.Intent
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class ShortcutInfoProviderShould {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val shortcutInfoProvider = ShortcutInfoProvider(context)

    val itemTaxi = ItemTaxi(
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
    )

    @Test
    fun getShortcutInfoFromItemTaxi() {
        val shortcut = shortcutInfoProvider.getShortcutInfoFromItemTaxi(itemTaxi)

        assertThat(shortcut.id).isEqualTo(itemTaxi.id.toString())
        assertThat(shortcut.shortLabel).isEqualTo(itemTaxi.name)
        assertThat(shortcut.longLabel).isEqualTo(itemTaxi.name)
    }

    @Test
    fun shortcutIntentActionIsActionDial() {
        val shortcut = shortcutInfoProvider.getShortcutInfoFromItemTaxi(itemTaxi)
        val intent = shortcut.intent

        assertThat(intent!!.action).isEqualTo(Intent.ACTION_DIAL)
    }

    @Test
    fun shortcutIntentDataHasPhoneNumber() {
        val shortcut = shortcutInfoProvider.getShortcutInfoFromItemTaxi(itemTaxi)
        val data = shortcut.intent!!.data

        assertThat(data!!.scheme).isEqualTo("tel")
        assertThat(data!!.schemeSpecificPart).isEqualTo(itemTaxi.phoneNumber)
    }
}