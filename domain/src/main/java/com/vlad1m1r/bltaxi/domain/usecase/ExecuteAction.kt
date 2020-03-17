package com.vlad1m1r.bltaxi.domain.usecase

import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

open class ExecuteAction(private val actionExecutor: ActionExecutor) {
    open operator fun invoke(action: Action) = actionExecutor.execute(action)
}
