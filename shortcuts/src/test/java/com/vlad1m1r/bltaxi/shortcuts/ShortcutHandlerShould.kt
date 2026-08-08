package com.vlad1m1r.bltaxi.shortcuts

import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import org.mockito.kotlin.*
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import org.junit.Test

class ShortcutHandlerShould {
    private val itemTaxi = ItemTaxi(
        id = 0,
        name = "name",
        phoneNumber = "phone_number",
        tariff1 = Tariff("start_price", "price_per_km", "hour_of_waiting"),
        tariff2 = Tariff("start_price_2", "price_per_km_2", "hour_of_waiting_2"),
        additionalInfo = "additional_info",
        viberNumber = "viber_number"
    )

    private val shortcutInfo = mock<ShortcutInfo>()
    private val shortcutManager = mock<ShortcutManager>()
    private val shortcutInfoProvider = mock<ShortcutInfoProvider> {
        on { getShortcutInfoFromItemTaxi(any()) }.thenReturn(shortcutInfo)
    }

    private val shortcutHandler: ShortcutHandler =
        ShortcutHandlerImpl(shortcutManager, shortcutInfoProvider)

    @Test
    fun addOnlyThreeShortcuts_whenArrayIsLonger() {

        val listOfTaxis = listOf(
            itemTaxi, itemTaxi, itemTaxi, itemTaxi, itemTaxi
        )

        shortcutHandler.addShortcutsForTaxis(listOfTaxis)

        verify(shortcutManager).dynamicShortcuts = listOf(shortcutInfo, shortcutInfo, shortcutInfo)
    }

    @Test
    fun removeOldShortcutsBeforeAddingNew() {

        val listOfTaxis = listOf(
            itemTaxi, itemTaxi, itemTaxi, itemTaxi, itemTaxi
        )

        shortcutHandler.addShortcutsForTaxis(listOfTaxis)
        inOrder(shortcutManager) {
            verify(shortcutManager).removeAllDynamicShortcuts()
            verify(shortcutManager).dynamicShortcuts =
                listOf(shortcutInfo, shortcutInfo, shortcutInfo)
        }
    }

    @Test
    fun notAddShortcuts_whenEmptyList() {

        shortcutHandler.addShortcutsForTaxis(emptyList())

        verifyNoMoreInteractions(shortcutManager)
    }
}