package com.vlad1m1r.actions

import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import javax.inject.Inject

class ActionExecutorImpl @Inject constructor(
    private val startActivity: StartActivity,
    shareExecutor: ShareExecutor,
    sendEmailExecutor: SendEmailExecutor,
    openPlayStoreExecutor: OpenPlayStoreExecutor,
    openUrlExecutor: OpenUrlExecutor,
    callNumberExecutor: CallNumberExecutor,
    callNumberOnViberExecutor: CallNumberOnViberExecutor
) : ActionExecutor {

    private val listOfExecutors = listOf(
        shareExecutor,
        sendEmailExecutor,
        openPlayStoreExecutor,
        openUrlExecutor,
        callNumberExecutor,
        callNumberOnViberExecutor
    )

    override fun execute(action: Action) {
        listOfExecutors.forEach {
            if(it.canHandleAction(action)) {
                startActivity(it(action))
            }
        }
    }
}