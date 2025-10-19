package com.vlad1m1r.baseui.theme

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryLightVariant,
    secondary = SecondaryLight,
    secondaryVariant = SecondaryLightVariant,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = ErrorColor,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight,
    onError = OnErrorColor
)

private val DarkColorScheme = darkColors(
    primary = PrimaryNight,
    primaryVariant = PrimaryNightVariant,
    secondary = SecondaryNight,
    secondaryVariant = SecondaryNightVariant,
    background = BackgroundNight,
    surface = SurfaceNight,
    error = ErrorColor,
    onPrimary = OnPrimaryNight,
    onSecondary = OnSecondaryNight,
    onBackground = OnBackgroundNight,
    onSurface = OnSurfaceNight,
    onError = OnErrorColor
)

@Composable
fun BLTaxiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // Find the Activity from the context (might be wrapped by Fragment)
            var context = view.context
            while (context !is Activity && context is android.content.ContextWrapper) {
                context = context.baseContext
            }

            if (context is Activity) {
                val window = context.window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colors = colorScheme,
        content = content
    )
}
