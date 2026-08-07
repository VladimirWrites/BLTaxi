package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.baseui.theme.LocalExtendedColors
import com.vlad1m1r.baseui.theme.TransparentColor
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.preview.TaxiPreviewParameterProvider

@Composable
internal fun TaxiCard(
    taxi: ItemTaxiViewModel,
    onCallClick: () -> Unit,
    onViberClick: () -> Unit,
    isDragging: Boolean,
    dragModifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDragging) 8.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top section with padding - draggable area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .then(dragModifier)
            ) {
                // Taxi name and phone number row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = taxi.itemTaxi.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.taxi__phone_number),
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = taxi.itemTaxi.phoneNumber,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
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

            // Prices table section - draggable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .then(dragModifier)
            ) {
                TariffHeaderRow()

                Spacer(modifier = Modifier.height(4.dp))

                TariffRow(
                    label = stringResource(R.string.taxi__tariff_1),
                    tariff = taxi.itemTaxi.tariff1
                )

                Spacer(modifier = Modifier.height(2.dp))

                TariffRow(
                    label = stringResource(R.string.taxi__tariff_2),
                    tariff = taxi.itemTaxi.tariff2
                )

                // Additional info (discount, etc.)
                val additionalInfo = taxi.itemTaxi.additionalInfo
                if (!additionalInfo.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = additionalInfo,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons section - NOT draggable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (taxi.isViberVisible) {
                        TaxiActionButton(
                            text = stringResource(R.string.taxi__viber),
                            onClick = onViberClick,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    TaxiActionButton(
                        text = stringResource(R.string.taxi__call),
                        onClick = onCallClick,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TariffHeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TariffHeaderCell(stringResource(R.string.taxi__start))
        TariffHeaderCell(stringResource(R.string.taxi__per_kilometer))
        TariffHeaderCell(stringResource(R.string.taxi__waiting))
    }
}

@Composable
private fun RowScope.TariffHeaderCell(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        modifier = Modifier.weight(1f)
    )
}

@Composable
private fun TariffRow(
    label: String,
    tariff: Tariff
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = tariff.start,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = tariff.pricePerKm,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = tariff.hourOfWaiting,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TaxiActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = TransparentColor,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CheckerPattern(
    modifier: Modifier = Modifier
) {
    // Theme-aware colors for proper contrast in both light and dark themes
    val extendedColors = LocalExtendedColors.current
    val darkSquare = extendedColors.checkerDarkSquare
    val lightSquare = extendedColors.checkerLightSquare

    val squareSize = 4.dp  // Size of each checker square

    Canvas(modifier = modifier) {
        val squareSizePx = squareSize.toPx()
        val width = size.width

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

                drawRect(
                    color = if (isGrey) darkSquare else lightSquare,
                    topLeft = Offset(col * squareSizePx, row * squareSizePx),
                    size = Size(squareSizePx, squareSizePx)
                )
            }
        }
    }
}

// Preview functions using PreviewParameterProvider
@Preview(showBackground = true, name = "Taxi Card Light")
@Composable
private fun TaxiCardLightPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = false) {
        TaxiCard(
            taxi = taxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = false
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111, name = "Taxi Card Dark")
@Composable
private fun TaxiCardDarkPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = true) {
        TaxiCard(
            taxi = taxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = false
        )
    }
}

@Preview(showBackground = true, name = "Taxi Card Dragging")
@Composable
private fun TaxiCardDraggingPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = false) {
        TaxiCard(
            taxi = taxi,
            onCallClick = {},
            onViberClick = {},
            isDragging = true  // Show elevated state
        )
    }
}
