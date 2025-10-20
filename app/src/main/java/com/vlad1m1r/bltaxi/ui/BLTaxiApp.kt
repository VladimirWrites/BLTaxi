package com.vlad1m1r.bltaxi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vlad1m1r.baseui.theme.BLTaxiTheme

/**
 * Root composable for the BL Taxi app.
 * Manages the navigation controller and wraps the app in the theme.
 *
 * @param modifier Modifier for styling
 */
@Composable
fun BLTaxiApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    BLTaxiTheme {
        BLTaxiScaffold(
            navController = navController,
            modifier = modifier.fillMaxSize()
        )
    }
}
