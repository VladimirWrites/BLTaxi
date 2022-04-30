package com.vlad1m1r.bltaxi.taxi.ui

import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.events.CallEvent
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.taxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.taxi.domain.usecase.GetOrderedTaxiList
import com.vlad1m1r.bltaxi.taxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.about.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.taxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import com.vlad1m1r.bltaxi.taxi.domain.usecase.IsViberInstalled
import com.vlad1m1r.bltaxi.taxi.ui.adapter.ItemTaxiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class TaxiViewModel @Inject constructor(
    private val saveTaxiOrder: SaveTaxiOrder,
    private val shortcutHandler: Provider<ShortcutHandler>,
    private val getOrderedTaxiList: GetOrderedTaxiList,
    private val executeAction: ExecuteAction,
    private val isViberInstalled: IsViberInstalled,
    private val tracker: Tracker,
    private val dispatchers: CoroutineDispatcherProvider
) : ViewModel() {

    private val mutableTaxis = MutableLiveData<List<ItemTaxiViewModel>>()
    val taxis: LiveData<List<ItemTaxiViewModel>> = mutableTaxis
    val isLoading = ObservableBoolean(false)
    val isErrorShown = ObservableBoolean(false)

    fun loadTaxis() {
        isLoading.set(true)
        isErrorShown.set(false)
        viewModelScope.launch(dispatchers.io) {
            val taxisResult = getOrderedTaxiList()
            withContext(dispatchers.main) {
                when (taxisResult) {
                    is TaxisResult.Success -> {
                        val viewModelList = taxisResult.list.map {
                            ItemTaxiViewModel(it, isViberInstalled(), ::callTaxi, ::callTaxiOnViber)
                        }
                        mutableTaxis.postValue(viewModelList)
                        isLoading.set(false)
                    }
                    is TaxisResult.Error -> {
                        isLoading.set(false)
                        isErrorShown.set(true)
                    }
                }
            }
        }
    }

    fun setTaxiOrder(viewModelList: List<ItemTaxiViewModel>) {
        val taxis = viewModelList.map { it.itemTaxi }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutHandler.get().addShortcutsForTaxis(taxis)
        }
        viewModelScope.launch(dispatchers.io) {
            saveTaxiOrder(taxis)
        }
    }

    private fun callTaxi(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.CALL))
        executeAction(Action.CallNumberAction(itemTaxi.phoneNumber))
    }

    private fun callTaxiOnViber(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.VIBER))
        executeAction(Action.CallNumberOnViberAction(itemTaxi.viberNumber!!))
    }
}
