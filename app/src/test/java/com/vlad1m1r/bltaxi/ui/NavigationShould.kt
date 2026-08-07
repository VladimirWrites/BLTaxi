package com.vlad1m1r.bltaxi.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NavigationShould {

    @Test
    fun haveCorrectRouteForTaxiScreen() {
        assertThat(Screen.Taxi.route).isEqualTo("taxi")
    }

    @Test
    fun haveCorrectRouteForSettingsScreen() {
        assertThat(Screen.Settings.route).isEqualTo("settings")
    }

    @Test
    fun haveCorrectRouteForAboutScreen() {
        assertThat(Screen.About.route).isEqualTo("about")
    }
}
