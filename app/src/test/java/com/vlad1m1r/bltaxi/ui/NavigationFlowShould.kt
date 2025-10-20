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

/**
 * Tests for navigation flows between screens.
 * Verifies navigation behavior including back navigation and menu-triggered navigation.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class NavigationFlowShould {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigateFromTaxiToSettings() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.Settings.route)
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Settings.route)
    }

    @Test
    fun navigateFromTaxiToAbout() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.About.route)
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.About.route)
    }

    @Test
    fun navigateBackFromSettingsToTaxi() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.Settings.route)
            navController.popBackStack()
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Taxi.route)
    }

    @Test
    fun navigateBackFromAboutToTaxi() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.About.route)
            navController.popBackStack()
        }

        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Taxi.route)
    }

    @Test
    fun preventDuplicateNavigationToSettings() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.Settings.route) {
                launchSingleTop = true
            }
            navController.navigate(Screen.Settings.route) {
                launchSingleTop = true
            }
        }

        // Back stack should only have Taxi and Settings, not Settings twice
        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Settings.route)
        navController.popBackStack()
        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Taxi.route)
    }

    @Test
    fun preventDuplicateNavigationToAbout() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            navController.setCurrentDestination(Screen.Taxi.route)
            navController.navigate(Screen.About.route) {
                launchSingleTop = true
            }
            navController.navigate(Screen.About.route) {
                launchSingleTop = true
            }
        }

        // Back stack should only have Taxi and About, not About twice
        assertThat(navController.currentDestination?.route).isEqualTo(Screen.About.route)
        navController.popBackStack()
        assertThat(navController.currentDestination?.route).isEqualTo(Screen.Taxi.route)
    }
}
