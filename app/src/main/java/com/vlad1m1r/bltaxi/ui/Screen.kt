package com.vlad1m1r.bltaxi.ui

/**
 * Sealed class representing all navigation destinations in the app.
 * Provides type-safe navigation routes.
 */
sealed class Screen(val route: String) {
    /**
     * Home screen displaying the list of available taxis.
     */
    object Taxi : Screen("taxi")

    /**
     * Settings screen for app configuration.
     */
    object Settings : Screen("settings")

    /**
     * About screen with app information and actions.
     */
    object About : Screen("about")
}
