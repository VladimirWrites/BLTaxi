package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel

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
                TaxiGrid(
                    taxis = state.taxis,
                    onCallClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxi(taxi))
                    },
                    onViberClick = { taxi ->
                        viewModel.sendAction(TaxiAction.CallTaxiOnViber(taxi))
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
private fun TaxiGrid(
    taxis: List<ItemTaxiViewModel>,
    onCallClick: (ItemTaxiViewModel) -> Unit,
    onViberClick: (ItemTaxiViewModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(taxis) { taxi ->
            TaxiCard(
                taxi = taxi,
                onCallClick = { onCallClick(taxi) },
                onViberClick = { onViberClick(taxi) }
            )
        }
    }
}

@Composable
private fun TaxiCard(
    taxi: ItemTaxiViewModel,
    onCallClick: () -> Unit,
    onViberClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Taxi name
            Text(
                text = taxi.itemTaxi.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Call button
                Button(
                    onClick = onCallClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Call",
                        fontSize = 14.sp
                    )
                }

                // Viber button (if available)
                if (taxi.isViberVisible) {
                    Button(
                        onClick = onViberClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF7360F2)
                        )
                    ) {
                        Text(
                            text = "Viber",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
