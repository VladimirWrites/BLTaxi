package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

class ExecuteAction(private val actionExecutor: ActionExecutor) {
    operator fun invoke(action: Action) = actionExecutor.execute(action)
}
