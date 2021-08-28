package com.vlad1m1r.bltaxi.about.domain.usecase

import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.ActionExecutor

class ExecuteAction(private val actionExecutor: ActionExecutor) {
    operator fun invoke(action: Action) = actionExecutor.execute(action)
}
