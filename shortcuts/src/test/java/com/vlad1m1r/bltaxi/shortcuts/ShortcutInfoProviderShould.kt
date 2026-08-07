package com.vlad1m1r.bltaxi.shortcuts

import android.content.Intent
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class ShortcutInfoProviderShould {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val shortcutInfoProvider = ShortcutInfoProvider(context)

    private val itemTaxi = ItemTaxi(
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
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
        val data = shortcut.intent!!.data!!

        assertThat(data.scheme).isEqualTo("tel")
        assertThat(data.schemeSpecificPart).isEqualTo(itemTaxi.phoneNumber)
    }
}