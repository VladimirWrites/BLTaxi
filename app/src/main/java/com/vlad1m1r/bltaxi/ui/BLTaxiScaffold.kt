package com.vlad1m1r.bltaxi.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vlad1m1r.bltaxi.about.ui.AboutScreen
import com.vlad1m1r.bltaxi.about.ui.AboutViewModel
import com.vlad1m1r.bltaxi.settings.ui.SettingsScreen
import com.vlad1m1r.bltaxi.settings.ui.SettingsViewModel
import com.vlad1m1r.bltaxi.taxi.ui.TaxiScreen
import com.vlad1m1r.bltaxi.taxi.ui.TaxiViewModel
import com.vlad1m1r.bltaxi.ui.components.BLTaxiTopAppBar

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
        Screen.Taxi.route -> stringResource(com.vlad1m1r.bltaxi.taxi.ui.R.string.app_name)
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
                onBackClick = {
                    navController.popBackStack()
                },
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
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Taxi.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Taxi Screen (Home) - with slide animations
            composable(
                route = Screen.Taxi.route,
                enterTransition = {
                    // When returning to Taxi (pop enter), slide in from left
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    // When leaving Taxi, slide out to left
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    // When returning to Taxi via back, slide in from left
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    // When leaving Taxi via back (shouldn't happen), slide out to right
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )
                }
            ) {
                val viewModel: TaxiViewModel = hiltViewModel()
                TaxiScreen(viewModel = viewModel)
            }

            // Settings Screen - with slide animations
            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    // When navigating to Settings, slide in from right
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    // When leaving Settings to another screen, slide out to left
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    // When returning to Settings (shouldn't happen), slide in from right
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    // When going back from Settings, slide out to right
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )
                }
            ) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(viewModel = viewModel)
            }

            // About Screen - with slide animations
            composable(
                route = Screen.About.route,
                enterTransition = {
                    // When navigating to About, slide in from right
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    // When leaving About to another screen, slide out to left
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    // When returning to About (shouldn't happen), slide in from right
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    // When going back from About, slide out to right
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )
                }
            ) {
                val viewModel: AboutViewModel = hiltViewModel()
                AboutScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
