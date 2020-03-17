package com.vlad1m1r.bltaxi.domain.interactor

import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

open class ActionInteractor(private val actionExecutor: ActionExecutor) {
        open fun execute(action: Action) = actionExecutor.execute(action)
}
