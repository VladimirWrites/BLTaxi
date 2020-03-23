package com.vlad1m1r.bltaxi.local.order

import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class OrderProviderShould {

    val sharedPreferencesEditor = mock<SharedPreferences.Editor>()
    val sharedPreferences = mock<SharedPreferences> {
        on { edit() }.thenReturn(sharedPreferencesEditor)
    }

    val orderProvider: OrderProvider = OrderProviderImpl(sharedPreferences)

    @Test
    fun getItemPosition() {
        whenever(sharedPreferences.getInt("item_position10", -1)).thenReturn(3)

        val result = orderProvider.getItemPosition(10)

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun setItemPosition() {
        orderProvider.setItemPosition(4, 5)

        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor).putInt("item_position4", 5)
            verify(sharedPreferencesEditor).apply()
            verifyNoMoreInteractions()
        }
    }
}