package com.vlad1m1r.bltaxi

import androidx.navigation.NavController
import com.vlad1m1r.bltaxi.taxi.TaxiNavigator

open class Navigator : TaxiNavigator {

    private var navController: NavController? = null

    override fun openAboutScreen() {
        navController?.navigate(R.id.action_taxiFragment_to_aboutFragment)
    }

    override fun openSettingsScreen() {
        navController?.navigate(R.id.action_taxiFragment_to_settingsFragment)
    }

    fun navigateUp() = navController?.navigateUp()

    open fun bind(navController: NavController) {
        this.navController = navController
    }

    open fun unbind() {
        navController = null
    }
}