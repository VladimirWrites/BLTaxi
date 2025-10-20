package com.vlad1m1r.bltaxi.ui.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.bltaxi.R

/**
 * Top app bar for BL Taxi app with dynamic title, back button, and overflow menu.
 *
 * @param title The title to display in the app bar
 * @param showBackButton Whether to show the back navigation button
 * @param onBackClick Callback when back button is clicked
 * @param showMenu Whether to show the overflow menu (only on home screen)
 * @param onSettingsClick Callback when Settings menu item is clicked
 * @param onAboutClick Callback when About menu item is clicked
 * @param modifier Modifier for styling
 */
@Composable
fun BLTaxiTopAppBar(
    title: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    showMenu: Boolean = true,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.content_description_navigate_back)
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if (showMenu) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.content_description_more_options)
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            onSettingsClick()
                        }
                    ) {
                        Text(stringResource(R.string.menu_settings))
                    }

                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            onAboutClick()
                        }
                    ) {
                        Text(stringResource(R.string.menu_about))
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Preview(name = "Top App Bar - Home")
@Composable
private fun BLTaxiTopAppBarHomePreview() {
    BLTaxiTheme {
        BLTaxiTopAppBar(
            title = "BL Taxi",
            showBackButton = false,
            onBackClick = {},
            onSettingsClick = {},
            onAboutClick = {}
        )
    }
}

@Preview(name = "Top App Bar - With Back Button")
@Composable
private fun BLTaxiTopAppBarWithBackPreview() {
    BLTaxiTheme {
        BLTaxiTopAppBar(
            title = "Settings",
            showBackButton = true,
            onBackClick = {},
            onSettingsClick = {},
            onAboutClick = {}
        )
    }
}

@Preview(name = "Top App Bar - Dark Theme")
@Composable
private fun BLTaxiTopAppBarDarkPreview() {
    BLTaxiTheme(darkTheme = true) {
        BLTaxiTopAppBar(
            title = "BL Taxi",
            showBackButton = false,
            onBackClick = {},
            onSettingsClick = {},
            onAboutClick = {}
        )
    }
}
