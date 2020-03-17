package com.vlad1m1r.bltaxi.taxi

import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad1m1r.basedata.exhaustive
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.events.CallEvent
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.domain.usecase.ExecuteAction
import com.vlad1m1r.bltaxi.domain.usecase.GetOrderedTaxiList
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.domain.usecase.SaveTaxiOrder
import com.vlad1m1r.bltaxi.shortcuts.ShortcutHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaxiViewModel(
    private val saveTaxiOrder: SaveTaxiOrder,
    private val shortcutHandler: ShortcutHandler,
    private val getOrderedTaxiList: GetOrderedTaxiList,
    private val executeAction: ExecuteAction,
    private val tracker: Tracker,
    private val dispatchers: CoroutineDispatcherProvider
) : ViewModel() {

    private val mutableTaxis = MutableLiveData<List<ItemTaxi>>()
    val taxis: LiveData<List<ItemTaxi>> = mutableTaxis
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
                        mutableTaxis.postValue(taxisResult.list)
                        isLoading.set(false)
                    }
                    is TaxisResult.Error -> {
                        isLoading.set(false)
                        isErrorShown.set(true)
                    }
                }.exhaustive
            }
        }
    }

    fun setTaxiOrder(taxis: List<ItemTaxi>) {
        viewModelScope.launch(dispatchers.io) {
            saveTaxiOrder(taxis)
        }
    }

    fun callTaxi(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.CALL))
        executeAction(Action.CallNumberAction(itemTaxi.phoneNumber))
    }

    fun callTaxiOnViber(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.VIBER))
        executeAction(Action.CallNumberOnViberAction(itemTaxi.viberNumber!!))
    }

    fun addShortcuts(taxis: List<ItemTaxi>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutHandler.addShortcutsForTaxis(taxis)
        }
    }
}
