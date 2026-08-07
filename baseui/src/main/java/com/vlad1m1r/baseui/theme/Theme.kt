package com.vlad1m1r.baseui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/** Above this relative luminance a surface counts as light, so system bar icons go dark. */
private const val LUMINANCE_THRESHOLD = 0.5f

// Extended colors for custom use cases that have no Material role
data class ExtendedColors(
    val checkerDarkSquare: Color,
    val checkerLightSquare: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        checkerDarkSquare = Color.Unspecified,
        checkerLightSquare = Color.Unspecified
    )
}

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryLightVariant,
    onPrimaryContainer = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryLightVariant,
    onSecondaryContainer = OnSurfaceLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = OnSurfaceLight,
    surfaceContainer = SurfaceLight,
    surfaceContainerLow = SurfaceLight,
    surfaceContainerLowest = BackgroundLight,
    surfaceContainerHigh = SurfaceLight,
    surfaceContainerHighest = SurfaceLight,
    outline = SecondaryLight,
    error = ErrorColor,
    onError = OnErrorColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryNight,
    onPrimary = OnPrimaryNight,
    primaryContainer = PrimaryNightVariant,
    onPrimaryContainer = OnSurfaceNight,
    secondary = SecondaryNight,
    onSecondary = OnSecondaryNight,
    secondaryContainer = SecondaryNightVariant,
    onSecondaryContainer = OnPrimaryNight,
    background = BackgroundNight,
    onBackground = OnBackgroundNight,
    surface = SurfaceNight,
    onSurface = OnSurfaceNight,
    surfaceVariant = SurfaceNight,
    onSurfaceVariant = OnSurfaceNight,
    surfaceContainer = SurfaceNight,
    surfaceContainerLow = SurfaceNight,
    surfaceContainerLowest = BackgroundNight,
    surfaceContainerHigh = SurfaceNight,
    surfaceContainerHighest = SurfaceNight,
    outline = SecondaryNight,
    error = ErrorColor,
    onError = OnErrorColor
)

@Composable
fun BLTaxiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) {
        ExtendedColors(
            checkerDarkSquare = CheckerDarkSquareNight,
            checkerLightSquare = CheckerLightSquareNight
        )
    } else {
        ExtendedColors(
            checkerDarkSquare = CheckerDarkSquareLight,
            checkerLightSquare = CheckerLightSquareLight
        )
    }

    // enableEdgeToEdge() derives system bar icon colors from the *system* dark mode, which is
    // wrong here twice over: the user can override the app theme, and the palette inverts
    // `primary` between themes, so the top app bar behind the status bar is dark in light mode
    // and light in dark mode. Pick the icon colors from what is actually drawn underneath.
    val view = LocalView.current
    if (!view.isInEditMode) {
        val lightStatusBarIcons = colorScheme.primary.luminance() > LUMINANCE_THRESHOLD
        val lightNavigationBarIcons = colorScheme.background.luminance() > LUMINANCE_THRESHOLD
        SideEffect {
            val window = view.context.findActivity()?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = lightStatusBarIcons
                isAppearanceLightNavigationBars = lightNavigationBarIcons
            }
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
