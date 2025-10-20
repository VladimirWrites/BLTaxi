package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.preview.TaxiListPreviewParameterProvider
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun TaxiScreen(
    viewModel: TaxiViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendAction(TaxiAction.LoadTaxis)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                LoadingIndicator()
            }
            state.isError -> {
                ErrorMessage()
            }
            else -> {
                TaxiList(
                    taxis = state.taxis,
                    onCallClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxi(taxi))
                    },
                    onViberClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxiOnViber(taxi))
                    },
                    onReorder = { from, to ->
                        val newList = state.taxis.toMutableList()
                        val item = newList.removeAt(from)
                        newList.add(to, item)
                        viewModel.sendAction(TaxiAction.ReorderTaxis(newList))
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error loading taxis",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun TaxiList(
    taxis: List<ItemTaxiViewModel>,
    onCallClick: (ItemTaxiViewModel) -> Unit,
    onViberClick: (ItemTaxiViewModel) -> Unit,
    onReorder: (Int, Int) -> Unit
) {
    val gridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
    val reorderableState = sh.calvin.reorderable.rememberReorderableLazyGridState(
        lazyGridState = gridState,
        onMove = { from, to ->
            onReorder(from.index, to.index)
        }
    )

    // Get navigation bar insets for bottom padding
    val navigationBarInsets = WindowInsets.navigationBars
    val density = LocalDensity.current
    val navigationBarBottomPadding = with(density) { navigationBarInsets.getBottom(density).toDp() }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        state = gridState,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 16.dp + navigationBarBottomPadding
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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
        Surface(color = MaterialTheme.colors.background) {
            TaxiList(
                taxis = taxis,
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
        Surface(color = MaterialTheme.colors.background) {
            TaxiList(
                taxis = taxis,
                onCallClick = {},
                onViberClick = {},
                onReorder = { _, _ -> }
            )
        }
    }
}
