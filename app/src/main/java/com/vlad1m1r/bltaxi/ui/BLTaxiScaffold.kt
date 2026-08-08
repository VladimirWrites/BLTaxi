package com.vlad1m1r.bltaxi.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vlad1m1r.bltaxi.about.ui.AboutScreen
import com.vlad1m1r.bltaxi.settings.ui.SettingsScreen
import com.vlad1m1r.bltaxi.taxi.ui.TaxiScreen
import com.vlad1m1r.bltaxi.ui.components.BLTaxiTopAppBar

private const val TRANSITION_DURATION_MS = 300

private fun AnimatedContentTransitionScope<*>.slideIn(fromRight: Boolean): EnterTransition =
    slideInHorizontally(
        initialOffsetX = { if (fromRight) it else -it },
        animationSpec = tween(TRANSITION_DURATION_MS)
    )

private fun AnimatedContentTransitionScope<*>.slideOut(toRight: Boolean): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { if (toRight) it else -it },
        animationSpec = tween(TRANSITION_DURATION_MS)
    )

/**
 * Main scaffold composable that manages the app's navigation structure and top app bar.
 *
 * Implements slide animations matching the old Fragment-based navigation:
 * - Enter: slide in from right
 * - Exit: slide out to left
 * - Pop enter: slide in from left
 * - Pop exit: slide out to right
 * - Duration: 300ms with standard easing
 *
 * @param navController The navigation controller for managing navigation
 * @param modifier Modifier for styling
 */
@Composable
fun BLTaxiScaffold(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine title based on current route
    val title = when (currentRoute) {
        Screen.Settings.route -> stringResource(com.vlad1m1r.bltaxi.settings.ui.R.string.settings__name)
        Screen.About.route -> stringResource(com.vlad1m1r.bltaxi.about.ui.R.string.about__name)
        else -> stringResource(com.vlad1m1r.bltaxi.taxi.ui.R.string.app_name)
    }

    // Show back button for all screens except Taxi (home)
    val showBackButton = currentRoute != Screen.Taxi.route

    // Show overflow menu only on Taxi (home) screen
    val showMenu = currentRoute == Screen.Taxi.route

    Scaffold(
        topBar = {
            BLTaxiTopAppBar(
                title = title,
                showBackButton = showBackButton,
                onBackClick = { navController.popBackStack() },
                showMenu = showMenu,
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route) {
                        launchSingleTop = true
                    }
                },
                onAboutClick = {
                    navController.navigate(Screen.About.route) {
                        launchSingleTop = true
                    }
                }
            )
        },
        modifier = modifier
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Taxi.route
        ) {
            // Taxi Screen (Home): always the left-most destination, so it slides in and out
            // to the left, except when it is popped (which cannot happen from the home screen).
            composable(
                route = Screen.Taxi.route,
                enterTransition = { slideIn(fromRight = false) },
                exitTransition = { slideOut(toRight = false) },
                popEnterTransition = { slideIn(fromRight = false) },
                popExitTransition = { slideOut(toRight = true) }
            ) {
                TaxiScreen(contentPadding = contentPadding)
            }

            // Settings Screen: slides in from the right, back navigation slides it out right.
            composable(
                route = Screen.Settings.route,
                enterTransition = { slideIn(fromRight = true) },
                exitTransition = { slideOut(toRight = false) },
                popEnterTransition = { slideIn(fromRight = true) },
                popExitTransition = { slideOut(toRight = true) }
            ) {
                SettingsScreen(contentPadding = contentPadding)
            }

            // About Screen: slides in from the right, back navigation slides it out right.
            composable(
                route = Screen.About.route,
                enterTransition = { slideIn(fromRight = true) },
                exitTransition = { slideOut(toRight = false) },
                popEnterTransition = { slideIn(fromRight = true) },
                popExitTransition = { slideOut(toRight = true) }
            ) {
                AboutScreen(contentPadding = contentPadding)
            }
        }
    }
}
