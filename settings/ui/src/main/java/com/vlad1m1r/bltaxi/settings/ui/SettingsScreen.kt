package com.vlad1m1r.bltaxi.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme

private val GroupShape = RoundedCornerShape(28.dp)
private val ScreenPadding = 16.dp

@Composable
fun SettingsScreen(
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showThemeDialog by remember { mutableStateOf(false) }

    // Theme changes are handled by BLTaxiApp observing SharedPreferences,
    // so there is no night mode effect to collect here.

    SettingsContent(
        state = state,
        contentPadding = contentPadding,
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
    contentPadding: PaddingValues,
    onAction: (SettingsAction) -> Unit,
    onThemePickerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        CategoryHeader(text = stringResource(R.string.settings__category_user_data))

        Group {
            SwitchPreference(
                title = stringResource(R.string.settings__analytics_title),
                summary = stringResource(R.string.settings__analytics_description),
                icon = Icons.Default.Analytics,
                checked = state.isAnalyticsEnabled,
                onCheckedChange = { onAction(SettingsAction.AnalyticsToggled(it)) }
            )
            SwitchPreference(
                title = stringResource(R.string.settings__crash_reporting_title),
                summary = stringResource(R.string.settings__crash_reporting_description),
                icon = Icons.Default.BugReport,
                checked = state.isCrashReportEnabled,
                onCheckedChange = { onAction(SettingsAction.CrashReportToggled(it)) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        CategoryHeader(text = stringResource(R.string.settings__category_theme))

        Group {
            ListPreference(
                title = stringResource(R.string.settings__pick_theme),
                summary = getThemeDisplayName(state.selectedTheme),
                icon = Icons.Default.Palette,
                onClick = onThemePickerClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Group(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenPadding),
        shape = GroupShape,
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp), content = content)
    }
}

@Composable
private fun CategoryHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            start = ScreenPadding + 16.dp,
            top = 24.dp,
            bottom = 10.dp,
            end = ScreenPadding
        )
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
            .padding(horizontal = ScreenPadding, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(ScreenPadding))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Switch(checked = checked, onCheckedChange = onCheckedChange)
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
            .padding(horizontal = ScreenPadding, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(ScreenPadding))

        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
    val themes = listOf(
        stringResource(R.string.theme_value_default) to stringResource(R.string.settings__theme_default),
        stringResource(R.string.theme_value_dark) to stringResource(R.string.settings__theme_dark),
        stringResource(R.string.theme_value_light) to stringResource(R.string.settings__theme_light)
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
                        RadioButton(selected = currentTheme == value, onClick = null)
                        Spacer(modifier = Modifier.width(ScreenPadding))
                        Text(text = label, style = MaterialTheme.typography.bodyLarge)
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

@Preview(showBackground = true, heightDp = 800, name = "Settings")
@Composable
private fun SettingsPreview() {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            SettingsContent(
                state = SettingsState(
                    selectedTheme = "theme_default",
                    isAnalyticsEnabled = true,
                    isCrashReportEnabled = false
                ),
                contentPadding = PaddingValues(),
                onAction = {},
                onThemePickerClick = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF17130B, heightDp = 800, name = "Settings, dark")
@Composable
private fun SettingsDarkPreview() {
    BLTaxiTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            SettingsContent(
                state = SettingsState(
                    selectedTheme = "theme_default",
                    isAnalyticsEnabled = true,
                    isCrashReportEnabled = false
                ),
                contentPadding = PaddingValues(),
                onAction = {},
                onThemePickerClick = {}
            )
        }
    }
}
