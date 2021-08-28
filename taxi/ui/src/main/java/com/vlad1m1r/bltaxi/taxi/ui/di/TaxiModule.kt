package com.vlad1m1r.bltaxi.taxi.ui.di

import com.vlad1m1r.bltaxi.about.domain.ActionExecutor
import com.vlad1m1r.bltaxi.taxi.domain.TaxiRepository
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
    fun provideGetTaxis(
        taxiRepository: TaxiRepository
    ): GetTaxis {
        return GetTaxis(taxiRepository)
    }

    @Provides
    fun provideSaveTaxiOrder(
        taxiRepository: TaxiRepository
    ): SaveTaxiOrder {
        return SaveTaxiOrder(taxiRepository)
    }

    @Provides
    fun provideGetTaxiPosition(
        taxiRepository: TaxiRepository
    ): GetTaxiPosition {
        return GetTaxiPosition(taxiRepository)
    }

    @Provides
    fun provideSaveTaxiPosition(
        taxiRepository: TaxiRepository
    ): SaveTaxiPosition {
        return SaveTaxiPosition(taxiRepository)
    }

    @Provides
    fun provideOrderTaxis(
        getTaxiPosition: GetTaxiPosition,
        saveTaxiOrder: SaveTaxiOrder
    ): SetOrderOfTaxis {
        return SetOrderOfTaxis(getTaxiPosition, saveTaxiOrder)
    }

    @Provides
    fun provideGetOrderedTaxiList(
        getTaxis: GetTaxis,
        setOrderOfTaxis: SetOrderOfTaxis
    ): GetOrderedTaxiList {
        return GetOrderedTaxiList(getTaxis, setOrderOfTaxis)
    }

    @Provides
    fun provideExecuteAction(
        actionExecutor: ActionExecutor
    ): ExecuteAction {
        return ExecuteAction(actionExecutor)
    }
}
