package com.vlad1m1r.bltaxi.settings.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showThemeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SettingsEffect.UpdateNightMode -> {
                    AppCompatDelegate.setDefaultNightMode(effect.mode)
                }
            }
        }
    }

    SettingsContent(
        state = state,
        onAction = { action -> viewModel.sendAction(action) },
        onThemePickerClick = { showThemeDialog = true }
    )

    if (showThemeDialog) {
        ThemePickerDialog(
            currentTheme = state.selectedTheme,
            onThemeSelected = { theme ->
                viewModel.sendAction(SettingsAction.ThemeChanged(theme))
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }
}

@Composable
private fun SettingsContent(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onThemePickerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        // User Data Category
        CategoryHeader(text = stringResource(R.string.settings__category_user_data))

        SwitchPreference(
            title = stringResource(R.string.settings__analytics_title),
            summary = stringResource(R.string.settings__analytics_description),
            icon = Icons.Default.Analytics,
            checked = state.isAnalyticsEnabled,
            onCheckedChange = { onAction(SettingsAction.AnalyticsToggled(it)) }
        )

        Divider()

        SwitchPreference(
            title = stringResource(R.string.settings__crash_reporting_title),
            summary = stringResource(R.string.settings__crash_reporting_description),
            icon = Icons.Default.BugReport,
            checked = state.isCrashReportEnabled,
            onCheckedChange = { onAction(SettingsAction.CrashReportToggled(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Theme Category
        CategoryHeader(text = stringResource(R.string.settings__category_theme))

        ListPreference(
            title = stringResource(R.string.settings__pick_theme),
            summary = getThemeDisplayName(state.selectedTheme),
            icon = Icons.Default.Palette,
            onClick = onThemePickerClick
        )
    }
}

@Composable
private fun CategoryHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.secondary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 72.dp, top = 16.dp, bottom = 8.dp, end = 16.dp)
    )
}

@Composable
private fun SwitchPreference(
    title: String,
    summary: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(end = 32.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = summary,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material.SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondary,
                checkedTrackColor = MaterialTheme.colors.secondary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun ListPreference(
    title: String,
    summary: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(end = 32.dp)
        )

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = summary,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ThemePickerDialog(
    currentTheme: String,
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val themeDefault = stringResource(R.string.theme_value_default)
    val themeDark = stringResource(R.string.theme_value_dark)
    val themeLight = stringResource(R.string.theme_value_light)

    val themes = listOf(
        themeDefault to stringResource(R.string.settings__theme_default),
        themeDark to stringResource(R.string.settings__theme_dark),
        themeLight to stringResource(R.string.settings__theme_light)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.settings__pick_theme)) },
        text = {
            Column {
                themes.forEach { (value, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = currentTheme == value,
                                onClick = { onThemeSelected(value) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentTheme == value,
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = label)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(android.R.string.ok))
            }
        }
    )
}

@Composable
private fun getThemeDisplayName(themeValue: String): String {
    return when (themeValue) {
        stringResource(R.string.theme_value_dark) -> stringResource(R.string.settings__theme_dark)
        stringResource(R.string.theme_value_light) -> stringResource(R.string.settings__theme_light)
        else -> stringResource(R.string.settings__theme_default)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsContent(
            state = SettingsState(
                selectedTheme = "theme_default",
                isAnalyticsEnabled = true,
                isCrashReportEnabled = true
            ),
            onAction = {},
            onThemePickerClick = {}
        )
    }
}
