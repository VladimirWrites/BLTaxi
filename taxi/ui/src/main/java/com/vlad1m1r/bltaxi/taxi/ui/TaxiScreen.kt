package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
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
    val lazyListState = androidx.compose.foundation.lazy.rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
        onMove = { from, to ->
            onReorder(from.index, to.index)
        }
    )

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(taxis, key = { it.itemTaxi.id }) { taxi ->
            ReorderableItem(reorderableState, key = taxi.itemTaxi.id) { isDragging ->
                TaxiCard(
                    taxi = taxi,
                    onCallClick = { onCallClick(taxi) },
                    onViberClick = { onViberClick(taxi) },
                    isDragging = isDragging,
                    modifier = Modifier
                        .longPressDraggableHandle()
                )
            }
        }
    }
}

@Composable
private fun CheckerPattern(
    modifier: Modifier = Modifier
) {
    val lightGray = Color(0xFFEEEEEE)
    val white = Color.White
    val squareSize = 4.dp  // Size of each checker square

    Canvas(modifier = modifier) {
        val squareSizePx = squareSize.toPx()
        val width = size.width
        val height = size.height

        // Calculate number of squares needed
        val numRows = 3  // Three rows as specified
        val numCols = (width / squareSizePx).toInt() + 1

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                // Alternate pattern: for even rows, start with grey; for odd rows, start with white
                val isGrey = if (row % 2 == 0) {
                    col % 2 == 0  // Even row: grey on even columns
                } else {
                    col % 2 != 0  // Odd row: grey on odd columns
                }

                val color = if (isGrey) lightGray else white

                drawRect(
                    color = color,
                    topLeft = Offset(col * squareSizePx, row * squareSizePx),
                    size = Size(squareSizePx, squareSizePx)
                )
            }
        }
    }
}

@Composable
private fun TaxiCard(
    taxi: ItemTaxiViewModel,
    onCallClick: () -> Unit,
    onViberClick: () -> Unit,
    isDragging: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = if (isDragging) 8.dp else 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top section with padding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                // Taxi name and phone number row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = taxi.itemTaxi.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = taxi.itemTaxi.phoneNumber,
                            style = MaterialTheme.typography.body1,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Checker pattern divider - full width, edge to edge
            CheckerPattern(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom section with padding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

            // Start price and kilometer price row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Start: ${taxi.itemTaxi.startPrice}",
                    style = MaterialTheme.typography.body1,
                    fontSize = 14.sp
                )
                Text(
                    text = "Kilometer: ${taxi.itemTaxi.pricePerKm}",
                    style = MaterialTheme.typography.body1,
                    fontSize = 14.sp
                )
            }

            // Additional info (discount, etc.)
            val additionalInfo = taxi.itemTaxi.additionalInfo
            if (!additionalInfo.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = additionalInfo,
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Viber button (if available)
                if (taxi.isViberVisible) {
                    OutlinedButton(
                        onClick = onViberClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.secondary
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            MaterialTheme.colors.secondary
                        )
                    ) {
                        Text(
                            text = "VIBER",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Call button
                OutlinedButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.secondary
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        MaterialTheme.colors.secondary
                    )
                ) {
                    Text(
                        text = "CALL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            }  // Close bottom padding column
        }  // Close outer column
    }  // Close card
}

// Preview functions
@Preview(showBackground = true, name = "Taxi Card Light")
@Composable
private fun TaxiCardPreview() {
    BLTaxiTheme(darkTheme = false) {
        val sampleTaxi = ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 1,
                name = "Avala Taxi",
                phoneNumber = "1500",
                startPrice = "2.00 KM",
                pricePerKm = "1.50 KM",
                additionalInfo = "20% discount on all rides",
                viberNumber = "1500"
            ),
            isViberButtonVisible = true,
            call = {},
            callViber = {}
        )
        TaxiCard(
            taxi = sampleTaxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = false
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111, name = "Taxi Card Dark")
@Composable
private fun TaxiCardDarkPreview() {
    BLTaxiTheme(darkTheme = true) {
        val sampleTaxi = ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 1,
                name = "Banja Luka Taxi",
                phoneNumber = "1544",
                startPrice = "2.00 KM",
                pricePerKm = "1.50 KM",
                additionalInfo = "20% discount on prices up to 20 KM",
                viberNumber = "1544"
            ),
            isViberButtonVisible = true,
            call = {},
            callViber = {}
        )
        TaxiCard(
            taxi = sampleTaxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = false
        )
    }
}

@Preview(showBackground = true, name = "Taxi Card Without Viber")
@Composable
private fun TaxiCardNoViberPreview() {
    BLTaxiTheme(darkTheme = false) {
        val sampleTaxi = ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 2,
                name = "Big Taxi",
                phoneNumber = "1511",
                startPrice = "2.00 KM",
                pricePerKm = "1.50 KM",
                additionalInfo = "20% discount on prices up to 15 KM",
                viberNumber = null
            ),
            isViberButtonVisible = false,
            call = {},
            callViber = {}
        )
        TaxiCard(
            taxi = sampleTaxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = false
        )
    }
}

@Preview(showBackground = true, name = "Taxi Card Dragging")
@Composable
private fun TaxiCardDraggingPreview() {
    BLTaxiTheme(darkTheme = false) {
        val sampleTaxi = ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 3,
                name = "Bel Taxi",
                phoneNumber = "1550",
                startPrice = "2.00 KM",
                pricePerKm = "1.80 KM",
                additionalInfo = "30% discount on prices up to 15 KM",
                viberNumber = "1550"
            ),
            isViberButtonVisible = true,
            call = {},
            callViber = {}
        )
        TaxiCard(
            taxi = sampleTaxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = true  // Show elevated state
        )
    }
}

@Preview(showBackground = true, heightDp = 800, name = "Taxi List Light")
@Composable
private fun TaxiListPreview() {
    BLTaxiTheme(darkTheme = false) {
        val sampleTaxis = listOf(
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 1,
                    name = "Avala Taxi",
                    phoneNumber = "1500",
                    startPrice = "2.00 KM",
                    pricePerKm = "1.50 KM",
                    additionalInfo = "20% discount on all rides",
                    viberNumber = "1500"
                ),
                isViberButtonVisible = true,
                call = {},
                callViber = {}
            ),
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 2,
                    name = "Banja Luka Taxi",
                    phoneNumber = "1544",
                    startPrice = "2.00 KM",
                    pricePerKm = "1.50 KM",
                    additionalInfo = "20% discount on prices up to 20 KM",
                    viberNumber = "1544"
                ),
                isViberButtonVisible = true,
                call = {},
                callViber = {}
            ),
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 3,
                    name = "Big Taxi",
                    phoneNumber = "1511",
                    startPrice = "2.00 KM",
                    pricePerKm = "1.50 KM",
                    additionalInfo = "20% discount on prices up to 15 KM",
                    viberNumber = null
                ),
                isViberButtonVisible = false,
                call = {},
                callViber = {}
            )
        )
        Surface(color = MaterialTheme.colors.background) {
            TaxiList(
                taxis = sampleTaxis,
                onCallClick = {},
                onViberClick = {},
                onReorder = { _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111, heightDp = 800, name = "Taxi List Dark")
@Composable
private fun TaxiListDarkPreview() {
    BLTaxiTheme(darkTheme = true) {
        val sampleTaxis = listOf(
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 1,
                    name = "Avala Taxi",
                    phoneNumber = "1500",
                    startPrice = "2.00 KM",
                    pricePerKm = "1.50 KM",
                    additionalInfo = "20% discount on all rides",
                    viberNumber = "1500"
                ),
                isViberButtonVisible = true,
                call = {},
                callViber = {}
            ),
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 2,
                    name = "Banja Luka Taxi",
                    phoneNumber = "1544",
                    startPrice = "2.00 KM",
                    pricePerKm = "1.50 KM",
                    additionalInfo = "20% discount on prices up to 20 KM",
                    viberNumber = "1544"
                ),
                isViberButtonVisible = true,
                call = {},
                callViber = {}
            )
        )
        Surface(color = MaterialTheme.colors.background) {
            TaxiList(
                taxis = sampleTaxis,
                onCallClick = {},
                onViberClick = {},
                onReorder = { _, _ -> }
            )
        }
    }
}
