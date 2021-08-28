package com.vlad1m1r.bltaxi.taxi.ui.di

import com.vlad1m1r.bltaxi.about.domain.ActionExecutor
import com.vlad1m1r.bltaxi.taxi.domain.Repository
import com.vlad1m1r.bltaxi.about.domain.usecase.*
import com.vlad1m1r.bltaxi.taxi.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TaxiModule {

    @Provides
    fun bindGetTaxis(
        repository: Repository
    ): GetTaxis {
        return GetTaxis(repository)
    }

    @Provides
    fun bindSaveTaxiOrder(
        repository: Repository
    ): SaveTaxiOrder {
        return SaveTaxiOrder(repository)
    }

    @Provides
    fun bindGetTaxiPosition(
        repository: Repository
    ): GetTaxiPosition {
        return GetTaxiPosition(repository)
    }

    @Provides
    fun bindSaveTaxiPosition(
        repository: Repository
    ): SaveTaxiPosition {
        return SaveTaxiPosition(repository)
    }

    @Provides
    fun bindOrderTaxis(
        getTaxiPosition: GetTaxiPosition,
        saveTaxiOrder: SaveTaxiOrder
    ): OrderTaxis {
        return OrderTaxis(getTaxiPosition, saveTaxiOrder)
    }

    @Provides
    fun bindGetOrderedTaxiList(
        getTaxis: GetTaxis,
        orderTaxis: OrderTaxis
    ): GetOrderedTaxiList {
        return GetOrderedTaxiList(getTaxis, orderTaxis)
    }

    @Provides
    fun bindExecuteAction(
        actionExecutor: ActionExecutor
    ): ExecuteAction {
        return ExecuteAction(actionExecutor)
    }
}
