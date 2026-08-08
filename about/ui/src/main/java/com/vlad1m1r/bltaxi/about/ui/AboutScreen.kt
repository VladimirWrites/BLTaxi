package com.vlad1m1r.bltaxi.about.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme

private val GroupShape = RoundedCornerShape(28.dp)
private val ScreenPadding = 16.dp

@Composable
fun AboutScreen(
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: AboutViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is AboutEffect.ShowError -> {
                    // Handle error effects if needed in the future
                }
            }
        }
    }

    AboutContent(
        state = state,
        contentPadding = contentPadding,
        onAction = { action -> viewModel.sendAction(action) }
    )
}

@Composable
private fun AboutContent(
    state: AboutState,
    contentPadding: PaddingValues,
    onAction: (AboutAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        AppMark(version = state.appVersion)

        Spacer(modifier = Modifier.height(8.dp))

        Group {
            AboutRow(
                text = stringResource(R.string.about__email),
                icon = Icons.Default.Email,
                onClick = { onAction(AboutAction.SendEmailClicked) }
            )
            AboutRow(
                text = stringResource(R.string.about__rate_app),
                icon = Icons.Default.Star,
                onClick = { onAction(AboutAction.RateAppClicked) }
            )
            AboutRow(
                text = stringResource(R.string.about__share_app),
                icon = Icons.Default.Share,
                onClick = { onAction(AboutAction.ShareAppClicked) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Group {
            AboutRow(
                text = stringResource(R.string.about__privacy_policy),
                icon = Icons.Default.Lock,
                onClick = { onAction(AboutAction.PrivacyPolicyClicked) }
            )
            AboutRow(
                text = stringResource(R.string.about__terms_and_conditions),
                icon = Icons.Default.Description,
                onClick = { onAction(AboutAction.TermsAndConditionsClicked) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AppMark(version: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenPadding, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialShapes.Clover4Leaf.toShape()
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalTaxi,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.about__app_name),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.about__version, version),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.about__author),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
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
private fun AboutRow(
    text: String,
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
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, heightDp = 800, name = "About")
@Composable
private fun AboutPreview() {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            AboutContent(
                state = AboutState(appVersion = "3.0.0"),
                contentPadding = PaddingValues(),
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF17130B, heightDp = 800, name = "About, dark")
@Composable
private fun AboutDarkPreview() {
    BLTaxiTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            AboutContent(
                state = AboutState(appVersion = "3.0.0"),
                contentPadding = PaddingValues(),
                onAction = {}
            )
        }
    }
}
