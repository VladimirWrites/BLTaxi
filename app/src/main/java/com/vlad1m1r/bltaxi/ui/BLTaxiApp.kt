package com.vlad1m1r.bltaxi.ui

import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
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
    val context = LocalContext.current
    val prefs = remember { PreferenceManager.getDefaultSharedPreferences(context) }

    val themeKey = context.getString(com.vlad1m1r.bltaxi.analytics.R.string.pref_key_theme_picker)
    val defaultTheme = context.getString(com.vlad1m1r.bltaxi.settings.ui.R.string.theme_value_default)
    val darkThemeValue = context.getString(com.vlad1m1r.bltaxi.settings.ui.R.string.theme_value_dark)
    val lightThemeValue = context.getString(com.vlad1m1r.bltaxi.settings.ui.R.string.theme_value_light)

    // Observable theme state
    var themePref by remember {
        mutableStateOf(prefs.getString(themeKey, defaultTheme) ?: defaultTheme)
    }

    // Listen for preference changes
    DisposableEffect(prefs, themeKey) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == themeKey) {
                themePref = prefs.getString(themeKey, defaultTheme) ?: defaultTheme
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    // Determine dark theme from preference
    val darkTheme = when (themePref) {
        darkThemeValue -> true
        lightThemeValue -> false
        else -> isSystemInDarkTheme() // System default
    }

    BLTaxiTheme(darkTheme = darkTheme) {
        BLTaxiScaffold(
            navController = navController,
            modifier = modifier.fillMaxSize()
        )
    }
}
