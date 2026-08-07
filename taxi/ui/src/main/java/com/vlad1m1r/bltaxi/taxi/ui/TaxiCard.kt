package com.vlad1m1r.bltaxi.taxi.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlad1m1r.baseui.theme.BLTaxiTheme
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import com.vlad1m1r.bltaxi.taxi.ui.preview.TaxiPreviewParameterProvider

private val CardCorner = 28.dp
private val CardShape = RoundedCornerShape(CardCorner)
private val CardPadding = 20.dp
private val ShortcodeSize = 56.dp
private val ShortcodeInset = 6.dp
private val ShortcodeMaxFontSize = 16.sp
private val ShortcodeMinFontSize = 9.sp
private val ActionHeight = 56.dp

/** titleLarge is 22sp; the figures sit below it so "25,00 KM/h" fits without clipping. */
private val FigureFontSize = 20.sp
private val FigureMinFontSize = 13.sp
private val FigureLineHeight = 26.sp

@Composable
internal fun TaxiCard(
    taxi: ItemTaxiViewModel,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onCallClick: () -> Unit,
    onViberClick: () -> Unit,
    isDragging: Boolean,
    modifier: Modifier = Modifier,
    dragModifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = CardShape,
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = if (isDragging) 6.dp else 0.dp,
        shadowElevation = if (isDragging) 6.dp else 0.dp
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            Header(
                taxi = taxi,
                isExpanded = isExpanded,
                onToggleExpanded = onToggleExpanded,
                onCallClick = onCallClick,
                modifier = dragModifier
            )

            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(
                        start = CardPadding,
                        end = CardPadding,
                        bottom = CardPadding
                    )
                ) {
                    // No drag handle here: the modifier registers one, and the library supports
                    // exactly one handle per item. Attaching it to the header as well made the
                    // drag read the wrong handle offset and pick the wrong neighbour.
                    TariffRow(taxi.itemTaxi.tariff1)

                    val additionalInfo = taxi.itemTaxi.additionalInfo
                    if (!additionalInfo.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        DiscountNote(text = additionalInfo)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TaxiActions(
                        phoneNumber = taxi.itemTaxi.phoneNumber,
                        isViberVisible = taxi.isViberVisible,
                        onCallClick = onCallClick,
                        onViberClick = onViberClick
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    taxi: ItemTaxiViewModel,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // The click sits on the header, not on the card, for two reasons. The header's height never
    // changes, so the ripple cannot stretch while the card animates open. And clipping it to the
    // card's own corners means the ripple still fills the whole card edge to edge when collapsed,
    // padding included, instead of showing up as a rectangle inside it.
    val rippleShape = if (isExpanded) {
        RoundedCornerShape(topStart = CardCorner, topEnd = CardCorner)
    } else {
        CardShape
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(rippleShape)
            .clickable(
                onClick = onToggleExpanded,
                onClickLabel = stringResource(
                    if (isExpanded) R.string.taxi__hide_details else R.string.taxi__show_details
                )
            )
            .padding(CardPadding)
            .heightIn(min = ShortcodeSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Shortcode(taxi.itemTaxi.phoneNumber)

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = taxi.itemTaxi.name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Collapsed, calling still has to be one tap — the primary action never hides.
        if (!isExpanded) {
            Spacer(modifier = Modifier.width(8.dp))
            FilledTonalIconButton(onClick = onCallClick) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = stringResource(
                        R.string.taxi__call_number,
                        taxi.itemTaxi.phoneNumber
                    )
                )
            }
        }

        val chevronRotation by animateFloatAsState(
            targetValue = if (isExpanded) 180f else 0f,
            label = "chevronRotation"
        )
        Icon(
            imageVector = Icons.Default.ExpandMore,
            contentDescription = null,
            modifier = Modifier.rotate(chevronRotation),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Shortcode(phoneNumber: String) {
    Box(
        modifier = Modifier
            .size(ShortcodeSize)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialShapes.Clover4Leaf.toShape()
            ),
        contentAlignment = Alignment.Center
    ) {
        // Most firms use a four-digit shortcode, but at least one publishes a full number. The
        // clover narrows towards its corners, so the text is inset and allowed to shrink; past
        // the floor it truncates rather than spilling over the shape.
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = ShortcodeInset),
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            autoSize = TextAutoSize.StepBased(
                minFontSize = ShortcodeMinFontSize,
                maxFontSize = ShortcodeMaxFontSize
            )
        )
    }
}

@Composable
private fun TariffRow(tariff: Tariff, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Figure(label = stringResource(R.string.taxi__start), value = tariff.start)
        Figure(label = stringResource(R.string.taxi__per_kilometer), value = tariff.pricePerKm)
        Figure(label = stringResource(R.string.taxi__waiting), value = tariff.hourOfWaiting)
    }
}

@Composable
private fun RowScope.Figure(label: String, value: String) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        // Values arrive from the feed with their units attached ("25,00 KM/h"), so the longest
        // one decides the width. Auto-sizing shrinks just that column instead of clipping it.
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            lineHeight = FigureLineHeight,
            maxLines = 1,
            softWrap = false,
            autoSize = TextAutoSize.StepBased(
                minFontSize = FigureMinFontSize,
                maxFontSize = FigureFontSize
            )
        )
    }
}

/**
 * Connected button group: the pair reads as one control, with the outer corners rounded and the
 * touching corners squared off. Shapes come from [ButtonGroupDefaults] so they stay in step with
 * the expressive spec.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TaxiActions(
    phoneNumber: String,
    isViberVisible: Boolean,
    onCallClick: () -> Unit,
    onViberClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
    ) {
        Button(
            onClick = onCallClick,
            modifier = Modifier
                .weight(1f)
                .height(ActionHeight),
            shape = if (isViberVisible) {
                ButtonGroupDefaults.connectedLeadingButtonShape
            } else {
                CardShape
            }
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.taxi__call_number, phoneNumber),
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        }

        if (isViberVisible) {
            Button(
                onClick = onViberClick,
                modifier = Modifier.height(ActionHeight),
                shape = ButtonGroupDefaults.connectedTrailingButtonShape,
                colors = ButtonDefaults.filledTonalButtonColors()
            ) {
                Text(
                    text = stringResource(R.string.taxi__viber),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1
                )
            }
        }
    }
}

/** Discount note, kept as the only tertiary-coloured element on the screen. */
@Composable
internal fun DiscountNote(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Sell,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true, name = "Collapsed")
@Composable
private fun TaxiCardCollapsedPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            TaxiCard(
                taxi = taxi,
                isExpanded = false,
                onToggleExpanded = {},
                onCallClick = {},
                onViberClick = {},
                isDragging = false,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Expanded")
@Composable
private fun TaxiCardExpandedPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            TaxiCard(
                taxi = taxi,
                isExpanded = true,
                onToggleExpanded = {},
                onCallClick = {},
                onViberClick = {},
                isDragging = false,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF17130B, name = "Expanded, dark")
@Composable
private fun TaxiCardExpandedDarkPreview(
    @PreviewParameter(TaxiPreviewParameterProvider::class) taxi: ItemTaxiViewModel
) {
    BLTaxiTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            TaxiCard(
                taxi = taxi,
                isExpanded = true,
                onToggleExpanded = {},
                onCallClick = {},
                onViberClick = {},
                isDragging = false,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
