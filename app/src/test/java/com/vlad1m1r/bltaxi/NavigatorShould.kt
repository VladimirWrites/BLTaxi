package com.vlad1m1r.bltaxi

import androidx.navigation.NavController
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.junit.Test

class NavigatorShould {

    private var navController = mock<NavController>()
    private val navigator: Navigator = Navigator()

    @Test
    fun openAboutScreen_whenBind() {
        navigator.bind(navController)

        navigator.openAboutScreen()

        verify(navController).navigate(R.id.action_taxiFragment_to_aboutFragment)
    }

    @Test
    fun openSettingsScreen() {
        navigator.bind(navController)

        navigator.openSettingsScreen()

        verify(navController).navigate(R.id.action_taxiFragment_to_settingsFragment)
    }

    @Test
    fun unbind() {
        navigator.bind(navController)
        navigator.unbind()
        navigator.openSettingsScreen()

        verifyNoMoreInteractions(navController)
    }

    @Test
    fun navigateUp() {
        navigator.bind(navController)

        navigator.navigateUp()

        verify(navController).navigateUp()
    }

    @Test
    fun doNothing_whenBind() {
        navigator.openAboutScreen()
        navigator.openSettingsScreen()
        navigator.navigateUp()
    }
}
