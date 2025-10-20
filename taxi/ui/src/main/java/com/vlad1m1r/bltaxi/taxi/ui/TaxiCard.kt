package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.baseui.theme.LocalExtendedColors
import com.vlad1m1r.baseui.theme.TransparentColor
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
        elevation = if (isDragging) 8.dp else 4.dp
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

            // Prices and additional info section - draggable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .then(dragModifier)
            ) {
                // Tariff 1
                Text(
                    text = "Tariff 1",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Start: ${taxi.itemTaxi.tariff1.start}",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Per km: ${taxi.itemTaxi.tariff1.pricePerKm}",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Wait: ${taxi.itemTaxi.tariff1.hourOfWaiting}",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tariff 2
                Text(
                    text = "Tariff 2",
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Start: ${taxi.itemTaxi.tariff2.start}",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Per km: ${taxi.itemTaxi.tariff2.pricePerKm}",
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Wait: ${taxi.itemTaxi.tariff2.hourOfWaiting}",
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
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons section - NOT draggable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
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
                                backgroundColor = TransparentColor,
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
                            backgroundColor = TransparentColor,
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
            }  // Close buttons column
        }  // Close outer column
    }  // Close card
}

@Composable
private fun CheckerPattern(
    modifier: Modifier = Modifier
) {
    // Use theme-aware colors for proper contrast in both light and dark themes
    val extendedColors = LocalExtendedColors.current
    val darkSquare = extendedColors.checkerDarkSquare

    val lightSquare = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.surface  // Card surface color (light gray) in light theme
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)  // Same as phone icon in dark theme
    }

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

                val color = if (isGrey) darkSquare else lightSquare

                drawRect(
                    color = color,
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
