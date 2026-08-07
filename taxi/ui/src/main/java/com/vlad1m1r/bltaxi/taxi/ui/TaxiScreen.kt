package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.preview.TaxiListPreviewParameterProvider
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

private val GRID_SPACING = 16.dp

@Composable
fun TaxiScreen(
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: TaxiViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendAction(TaxiAction.LoadTaxis)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                LoadingIndicator(contentPadding)
            }
            state.isError -> {
                ErrorMessage(
                    contentPadding = contentPadding,
                    onTryAgainClick = { viewModel.sendAction(TaxiAction.LoadTaxis) }
                )
            }
            else -> {
                TaxiList(
                    taxis = state.taxis,
                    contentPadding = contentPadding,
                    onCallClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxi(taxi))
                    },
                    onViberClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxiOnViber(taxi))
                    },
                    onReorder = { from, to ->
                        viewModel.sendAction(TaxiAction.MoveTaxi(from, to))
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(
    contentPadding: PaddingValues,
    onTryAgainClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(GRID_SPACING),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.taxi__title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.taxi__message),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(onClick = onTryAgainClick) {
                Text(text = stringResource(R.string.taxi__try_again))
            }
        }
    }
}

@Composable
private fun TaxiList(
    taxis: List<ItemTaxiViewModel>,
    contentPadding: PaddingValues,
    onCallClick: (ItemTaxiViewModel) -> Unit,
    onViberClick: (ItemTaxiViewModel) -> Unit,
    onReorder: (Int, Int) -> Unit
) {
    val gridState = rememberLazyGridState()
    val reorderableState = rememberReorderableLazyGridState(
        lazyGridState = gridState,
        onMove = { from, to ->
            onReorder(from.index, to.index)
        }
    )

    val layoutDirection = LocalLayoutDirection.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        state = gridState,
        // Insets go to contentPadding, not to a Modifier.padding, so the list keeps
        // scrolling behind the system bars.
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(layoutDirection) + GRID_SPACING,
            end = contentPadding.calculateEndPadding(layoutDirection) + GRID_SPACING,
            top = contentPadding.calculateTopPadding() + GRID_SPACING,
            bottom = contentPadding.calculateBottomPadding() + GRID_SPACING
        ),
        verticalArrangement = Arrangement.spacedBy(GRID_SPACING),
        horizontalArrangement = Arrangement.spacedBy(GRID_SPACING),
        modifier = Modifier.fillMaxSize()
    ) {
        items(taxis, key = { it.itemTaxi.id }) { taxi ->
            ReorderableItem(reorderableState, key = taxi.itemTaxi.id) { isDragging ->
                TaxiCard(
                    taxi = taxi,
                    onCallClick = { onCallClick(taxi) },
                    onViberClick = { onViberClick(taxi) },
                    isDragging = isDragging,
                    dragModifier = Modifier.longPressDraggableHandle()
                )
            }
        }
    }
}

// Preview functions using PreviewParameterProvider
@Preview(showBackground = true, heightDp = 800, name = "Taxi List Light")
@Composable
private fun TaxiListLightPreview(
    @PreviewParameter(TaxiListPreviewParameterProvider::class) taxis: List<ItemTaxiViewModel>
) {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaxiList(
                taxis = taxis,
                contentPadding = PaddingValues(),
                onCallClick = {},
                onViberClick = {},
                onReorder = { _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111, heightDp = 800, name = "Taxi List Dark")
@Composable
private fun TaxiListDarkPreview(
    @PreviewParameter(TaxiListPreviewParameterProvider::class) taxis: List<ItemTaxiViewModel>
) {
    BLTaxiTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaxiList(
                taxis = taxis,
                contentPadding = PaddingValues(),
                onCallClick = {},
                onViberClick = {},
                onReorder = { _, _ -> }
            )
        }
    }
}
