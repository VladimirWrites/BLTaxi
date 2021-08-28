package com.vlad1m1r.bltaxi.shortcuts

import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import org.junit.Test

class ShortcutHandlerShould {
    private val itemTaxi = ItemTaxi(
        0,
        "name",
        "phone_number",
        "start_price",
        "price_per_km",
        "additional_info",
        "viber_number"
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