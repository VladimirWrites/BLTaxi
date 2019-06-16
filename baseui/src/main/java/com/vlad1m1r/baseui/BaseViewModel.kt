package com.vlad1m1r.baseui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel( private val mainDispatcher: CoroutineDispatcher) : ViewModel(), CoroutineScope {
    private val parentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = parentJob + mainDispatcher

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}