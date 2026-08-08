package com.vlad1m1r.bltaxi.taxi.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.domain.model.Tariff
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel

class TaxiPreviewParameterProvider : PreviewParameterProvider<ItemTaxiViewModel> {
    override val values: Sequence<ItemTaxiViewModel> = sequenceOf(
        // Taxi with Viber
        ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 1,
                name = "Avala Taxi",
                phoneNumber = "1500",
                tariff1 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,00 KM",
                    hourOfWaiting = "25,00 KM/h"
                ),
                tariff2 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,35 KM",
                    hourOfWaiting = "30,00 KM/h"
                ),
                additionalInfo = "20% discount on all rides",
                viberNumber = "1500"
            ),
            isViberButtonVisible = true,
            call = {},
            callViber = {}
        ),
        // Taxi without Viber
        ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 2,
                name = "Big Taxi",
                phoneNumber = "1511",
                tariff1 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,00 KM",
                    hourOfWaiting = "25,00 KM/h"
                ),
                tariff2 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,35 KM",
                    hourOfWaiting = "30,00 KM/h"
                ),
                additionalInfo = "20% discount on prices up to 15 KM",
                viberNumber = null
            ),
            isViberButtonVisible = false,
            call = {},
            callViber = {}
        ),
        // Taxi with longer discount text
        ItemTaxiViewModel(
            itemTaxi = ItemTaxi(
                id = 3,
                name = "Banja Luka Taxi",
                phoneNumber = "1544",
                tariff1 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,00 KM",
                    hourOfWaiting = "25,00 KM/h"
                ),
                tariff2 = Tariff(
                    start = "2,50 KM",
                    pricePerKm = "2,35 KM",
                    hourOfWaiting = "30,00 KM/h"
                ),
                additionalInfo = "20% discount on prices up to 20 KM",
                viberNumber = "1544"
            ),
            isViberButtonVisible = true,
            call = {},
            callViber = {}
        )
    )
}

class TaxiListPreviewParameterProvider : PreviewParameterProvider<List<ItemTaxiViewModel>> {
    override val values: Sequence<List<ItemTaxiViewModel>> = sequenceOf(
        listOf(
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 1,
                    name = "Avala Taxi",
                    phoneNumber = "1500",
                    tariff1 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,00 KM",
                        hourOfWaiting = "25,00 KM/h"
                    ),
                    tariff2 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,35 KM",
                        hourOfWaiting = "30,00 KM/h"
                    ),
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
                    tariff1 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,00 KM",
                        hourOfWaiting = "25,00 KM/h"
                    ),
                    tariff2 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,35 KM",
                        hourOfWaiting = "30,00 KM/h"
                    ),
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
                    tariff1 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,00 KM",
                        hourOfWaiting = "25,00 KM/h"
                    ),
                    tariff2 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,35 KM",
                        hourOfWaiting = "30,00 KM/h"
                    ),
                    additionalInfo = "20% discount on prices up to 15 KM",
                    viberNumber = null
                ),
                isViberButtonVisible = false,
                call = {},
                callViber = {}
            ),
            ItemTaxiViewModel(
                itemTaxi = ItemTaxi(
                    id = 4,
                    name = "Bel Taxi",
                    phoneNumber = "1550",
                    tariff1 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,00 KM",
                        hourOfWaiting = "25,00 KM/h"
                    ),
                    tariff2 = Tariff(
                        start = "2,50 KM",
                        pricePerKm = "2,35 KM",
                        hourOfWaiting = "30,00 KM/h"
                    ),
                    additionalInfo = "30% discount on prices up to 15 KM",
                    viberNumber = "1550"
                ),
                isViberButtonVisible = true,
                call = {},
                callViber = {}
            )
        )
    )
}
