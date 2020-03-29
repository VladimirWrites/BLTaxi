package com.vlad1m1r.bltaxi

import androidx.navigation.NavController
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class NavigatorShould {

    var navController = mock<NavController>()
    val navigator: Navigator = Navigator()

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
