package com.vlad1m1r.bltaxi.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class NavigationShould {

    @get:Rule
    val composeTestRule = createComposeRule()

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

    @Test
    fun startWithTaxiScreenAsDefaultDestination() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Taxi.route)
    }

    @Test
    fun navigateToSettingsScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.Settings.route)
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Settings.route)
    }
}
