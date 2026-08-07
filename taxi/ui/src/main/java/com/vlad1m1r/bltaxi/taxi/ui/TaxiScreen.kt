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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
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
import sh.calvin.reorderable.rememberReorderableLazyStaggeredGridState

private val ScreenPadding = 16.dp
private val CardSpacing = 16.dp

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
                LoadingState(contentPadding)
            }
            state.isError -> {
                ErrorState(
                    contentPadding = contentPadding,
                    onTryAgainClick = { viewModel.sendAction(TaxiAction.LoadTaxis) }
                )
            }
            else -> {
                TaxiList(
                    taxis = state.taxis,
                    contentPadding = contentPadding,
                    onCallClick = { taxi -> viewModel.sendAction(TaxiAction.CallTaxi(taxi)) },
                    onViberClick = { taxi -> viewModel.sendAction(TaxiAction.CallTaxiOnViber(taxi)) },
                    onOrderChanged = viewModel::saveOrder
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LoadingState(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator()
    }
}

@Composable
private fun ErrorState(
    contentPadding: PaddingValues,
    onTryAgainClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(ScreenPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
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
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onTryAgainClick) {
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
    onOrderChanged: (List<ItemTaxiViewModel>) -> Unit
) {
    // The dragged order is local Compose state, not the ViewModel's.
    //
    // onMove must have applied the move by the time it returns — the library then blocks on the
    // grid's layoutInfo updating before it will accept another one. Routing the change through a
    // StateFlow and back would land it a recomposition later than that contract allows, so the
    // list being dragged is a SnapshotStateList here and the ViewModel is told once, on drop.
    val orderedTaxis = remember { mutableStateListOf<ItemTaxiViewModel>() }
    LaunchedEffect(taxis) {
        // A plain comparison is enough now that a drop no longer writes back to the ViewModel:
        // upstream only changes on a real load, and then it should win — including when a sync
        // brings new prices for the same taxis.
        if (orderedTaxis.toList() != taxis) {
            orderedTaxis.clear()
            orderedTaxis.addAll(taxis)
        }
    }

    val gridState = rememberLazyStaggeredGridState()
    val reorderableState = rememberReorderableLazyStaggeredGridState(
        lazyStaggeredGridState = gridState,
        onMove = { from, to ->
            // Swap, not remove-and-insert.
            //
            // The grid picks a target by finding the item whose rectangle the dragged card
            // overlaps, and expects those two to trade places. Remove-and-insert — which is what
            // the LazyColumn variant of this library wants — shifts every item in between, so in
            // a grid the cards after the target get pulled up a row. That was the "items from
            // below jump up" behaviour.
            val fromIndex = from.index
            val toIndex = to.index
            if (fromIndex in orderedTaxis.indices && toIndex in orderedTaxis.indices) {
                val target = orderedTaxis[toIndex]
                orderedTaxis[toIndex] = orderedTaxis[fromIndex]
                orderedTaxis[fromIndex] = target
            }
        }
    )

    // Keyed by taxi id rather than list position, so expansion follows a card through a reorder
    // and survives configuration changes.
    val expandedIds = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf<Long>() }

    val layoutDirection = LocalLayoutDirection.current

    // Staggered, because an expanded card is much taller than a collapsed one. In a uniform
    // grid every card in a row is padded to the tallest, so expanding one leaves a hole beside
    // it; staggered lets the neighbouring column keep packing.
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
        state = gridState,
        // Insets go to contentPadding, not to a Modifier.padding, so the list keeps
        // scrolling behind the system bars.
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(layoutDirection) + ScreenPadding,
            end = contentPadding.calculateEndPadding(layoutDirection) + ScreenPadding,
            top = contentPadding.calculateTopPadding() + ScreenPadding,
            bottom = contentPadding.calculateBottomPadding() + ScreenPadding
        ),
        verticalItemSpacing = CardSpacing,
        horizontalArrangement = Arrangement.spacedBy(CardSpacing),
        modifier = Modifier.fillMaxSize()
    ) {
        items(orderedTaxis, key = { it.itemTaxi.id }) { taxi ->
            ReorderableItem(reorderableState, key = taxi.itemTaxi.id) { isDragging ->
                val id = taxi.itemTaxi.id
                TaxiCard(
                    taxi = taxi,
                    isExpanded = id in expandedIds,
                    onToggleExpanded = {
                        if (!expandedIds.remove(id)) expandedIds.add(id)
                    },
                    onCallClick = { onCallClick(taxi) },
                    onViberClick = { onViberClick(taxi) },
                    isDragging = isDragging,
                    // Publish once the finger lifts, not on every position it passes through.
                    dragModifier = Modifier.longPressDraggableHandle(
                        onDragStopped = { onOrderChanged(orderedTaxis.toList()) }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900, name = "Taxi list")
@Composable
private fun TaxiListPreview(
    @PreviewParameter(TaxiListPreviewParameterProvider::class) taxis: List<ItemTaxiViewModel>
) {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            TaxiList(
                taxis = taxis,
                contentPadding = PaddingValues(),
                onCallClick = {},
                onViberClick = {},
                onOrderChanged = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF17130B, heightDp = 900, name = "Taxi list, dark")
@Composable
private fun TaxiListDarkPreview(
    @PreviewParameter(TaxiListPreviewParameterProvider::class) taxis: List<ItemTaxiViewModel>
) {
    BLTaxiTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            TaxiList(
                taxis = taxis,
                contentPadding = PaddingValues(),
                onCallClick = {},
                onViberClick = {},
                onOrderChanged = {}
            )
        }
    }
}
