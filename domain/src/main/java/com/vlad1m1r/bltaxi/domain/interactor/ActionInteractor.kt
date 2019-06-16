package com.vlad1m1r.bltaxi.domain.interactor

import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

class ActionInteractor(private val actionExecutor: ActionExecutor) {
        fun execute(action: Action) = actionExecutor.execute(action)
}