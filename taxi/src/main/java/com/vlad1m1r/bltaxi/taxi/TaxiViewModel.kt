package com.vlad1m1r.bltaxi.taxi

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlad1m1r.basedata.exhaustive
import com.vlad1m1r.baseui.BaseViewModel
import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.analytics.Tracker
import com.vlad1m1r.bltaxi.analytics.events.CallEvent
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.TaxisResult
import com.vlad1m1r.bltaxi.domain.interactor.ActionInteractor
import com.vlad1m1r.bltaxi.domain.interactor.TaxiInteractor
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaxiViewModel(
    private val taxiInteractor: TaxiInteractor,
    private val actionInteractor: ActionInteractor,
    private val tracker: Tracker,
    private val dispatchers: CoroutineDispatcherProvider
) : BaseViewModel(dispatchers.main) {
    private val mutableTaxis = MutableLiveData<List<ItemTaxi>>()
    val taxis: LiveData<List<ItemTaxi>> = mutableTaxis
    val isLoading = ObservableBoolean(false)
    val isErrorShown = ObservableBoolean(false)

    fun loadTaxis() {
        isLoading.set(true)
        isErrorShown.set(false)
        viewModelScope.launch(dispatchers.io) {
            val taxisResult = taxiInteractor()
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
            taxiInteractor.setOrder(taxis)
        }
    }

    fun callTaxi(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.CALL))
        actionInteractor.execute(Action.CallNumberAction(itemTaxi.phoneNumber))
    }

    fun callTaxiOnViber(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.VIBER))
        actionInteractor.execute(Action.CallNumberOnViberAction(itemTaxi.viberNumber!!))
    }
}
