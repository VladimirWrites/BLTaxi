package com.vlad1m1r.actions

import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

class ActionExecutorImpl(
    private val startActivity: StartActivity,
    private val listOfExecutors: List<Executor>
) : ActionExecutor {
    override fun execute(action: Action) {
        listOfExecutors.forEach {
            if(it.canHandleAction(action)) {
                startActivity(it(action))
            }
        }
    }
}