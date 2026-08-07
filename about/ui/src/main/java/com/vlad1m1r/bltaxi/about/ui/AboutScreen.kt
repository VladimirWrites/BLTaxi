package com.vlad1m1r.bltaxi.about.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme

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
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Author text
                Text(
                    text = stringResource(R.string.about__author),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Version text
                Text(
                    text = stringResource(R.string.about__version, state.appVersion),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Action buttons
                AboutActionButton(
                    text = stringResource(R.string.about__email),
                    icon = Icons.Default.Email,
                    onClick = { onAction(AboutAction.SendEmailClicked) }
                )

                AboutActionButton(
                    text = stringResource(R.string.about__rate_app),
                    icon = Icons.Default.Star,
                    onClick = { onAction(AboutAction.RateAppClicked) }
                )

                AboutActionButton(
                    text = stringResource(R.string.about__share_app),
                    icon = Icons.Default.Share,
                    onClick = { onAction(AboutAction.ShareAppClicked) }
                )

                AboutActionButton(
                    text = stringResource(R.string.about__privacy_policy),
                    icon = Icons.Default.Lock,
                    onClick = { onAction(AboutAction.PrivacyPolicyClicked) }
                )

                AboutActionButton(
                    text = stringResource(R.string.about__terms_and_conditions),
                    icon = Icons.Default.Description,
                    onClick = { onAction(AboutAction.TermsAndConditionsClicked) }
                )
            }
        }
    }
}

@Composable
private fun AboutActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    BLTaxiTheme {
        AboutContent(
            state = AboutState(appVersion = "1.2.3"),
            contentPadding = PaddingValues(),
            onAction = {}
        )
    }
}
