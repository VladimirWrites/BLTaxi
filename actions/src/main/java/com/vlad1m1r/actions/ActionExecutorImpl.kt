package com.vlad1m1r.actions

import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor

class ActionExecutorImpl(
    private val shareExecutor: ShareExecutor,
    private val sendEmailExecutor: SendEmailExecutor,
    private val openPlayStoreExecutor: OpenPlayStoreExecutor,
    private val openUrlExecutor: OpenUrlExecutor,
    private val callNumberExecutor: CallNumberExecutor,
    private val callNumberOnViberExecutor: CallNumberOnViberExecutor
) : ActionExecutor {
    override fun execute(action: Action) {
        when (action) {
            is Action.ShareAction -> {
                shareExecutor(action)
            }
            is Action.SendEmailAction -> {
                sendEmailExecutor(action)
            }
            is Action.OpenPlayStoreAction -> {
                openPlayStoreExecutor(action)
            }
            is Action.OpenUrlAction -> {
                openUrlExecutor(action)
            }
            is Action.CallNumberAction -> {
                callNumberExecutor(action)
            }
            is Action.CallNumberOnViberAction -> {
                callNumberOnViberExecutor(action)
            }
        }
    }

}