package com.vlad1m1r.bltaxi.taxi.ui

import android.os.Build
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    // Actions flow - UI sends actions to ViewModel
    private val _actions = MutableSharedFlow<TaxiAction>()
    private val actions: SharedFlow<TaxiAction> = _actions.asSharedFlow()

    // State flow - ViewModel exposes state to UI
    private val _state = MutableStateFlow(TaxiState())
    val state: StateFlow<TaxiState> = _state.asStateFlow()

    // Effects flow - One-time events
    private val _effects = MutableSharedFlow<TaxiEffect>()
    val effects: SharedFlow<TaxiEffect> = _effects.asSharedFlow()

    init {
        observeActions()
    }

    private fun observeActions() {
        viewModelScope.launch {
            actions.collect { action ->
                handleAction(action)
            }
        }
    }

    fun sendAction(action: TaxiAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    /**
     * Loads the list unless it is already there.
     *
     * The screen asks for this every time it enters composition, which includes every rotation
     * and every return from Settings or About. Reloading each time threw the list away and
     * flashed the spinner; the ViewModel outlives all of those, so its data is still good.
     */
    fun loadTaxisIfNeeded() {
        val current = _state.value
        if (current.taxis.isEmpty() && !current.isLoading) {
            sendAction(TaxiAction.LoadTaxis)
        }
    }

    private fun handleAction(action: TaxiAction) {
        when (action) {
            TaxiAction.LoadTaxis -> loadTaxis()
            is TaxiAction.CallTaxi -> callTaxi(action.taxiViewModel.itemTaxi)
            is TaxiAction.CallTaxiOnViber -> callTaxiOnViber(action.taxiViewModel.itemTaxi)
        }
    }

    /**
     * Persists an order the user produced by dragging and refreshes the launcher shortcuts.
     *
     * Called once when a drag finishes, not on every position the finger crosses. While the drag
     * is in flight the list lives in the UI as Compose state, because the drag library needs each
     * move applied before its callback returns — see TaxiList.
     */
    fun saveOrder(reordered: List<ItemTaxiViewModel>) {
        if (reordered.isEmpty()) return

        // Deliberately no _state update.
        //
        // This runs from onDragStopped, while the drag library is still animating the dropped
        // card into place and the other cards with it. Emitting new state here recomposes the
        // grid and forces a relayout in the middle of that animation, which makes the whole list
        // visibly shift on drop even though the order is already correct.
        //
        // The list on screen is owned by the composable for as long as it is on screen; the
        // ViewModel only needs the order in order to persist it. A later load reads it back from
        // storage.

        val taxis = reordered.map { it.itemTaxi }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutHandler.get().addShortcutsForTaxis(taxis)
        }
        viewModelScope.launch(dispatchers.io) {
            saveTaxiOrder(taxis)
        }
    }

    private fun loadTaxis() {
        _state.update { it.copy(isLoading = true, isError = false) }

        viewModelScope.launch(dispatchers.io) {
            val taxisResult = getOrderedTaxiList()
            withContext(dispatchers.main) {
                when (taxisResult) {
                    is TaxisResult.Success -> {
                        val viewModelList = taxisResult.list.map {
                            ItemTaxiViewModel(it, isViberInstalled(), ::callTaxi, ::callTaxiOnViber)
                        }
                        _state.update { it.copy(taxis = viewModelList, isLoading = false, isError = false) }
                    }
                    is TaxisResult.Error -> {
                        _state.update { it.copy(isLoading = false, isError = true) }
                    }
                }
            }
        }
    }


    private fun callTaxi(itemTaxi: ItemTaxi) {
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.CALL))
        executeAction(Action.CallNumberAction(itemTaxi.phoneNumber))
    }

    private fun callTaxiOnViber(itemTaxi: ItemTaxi) {
        val viberNumber = itemTaxi.viberNumber
        if (viberNumber.isNullOrBlank()) return
        tracker.track(CallEvent(itemTaxi.id, itemTaxi.name, CallEvent.CallVariant.VIBER))
        executeAction(Action.CallNumberOnViberAction(viberNumber))
    }
}
