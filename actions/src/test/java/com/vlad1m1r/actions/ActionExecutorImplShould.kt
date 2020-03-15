package com.vlad1m1r.actions

import com.nhaarman.mockitokotlin2.*
import com.vlad1m1r.actions.executors.*
import com.vlad1m1r.bltaxi.domain.Action
import org.junit.Test

class ActionExecutorImplShould {

    private val shareExecutor = mock<ShareExecutor>()
    private val sendEmailExecutor = mock<SendEmailExecutor>()
    private val openPlayStoreExecutor = mock<OpenPlayStoreExecutor>()
    private val openUrlExecutor = mock<OpenUrlExecutor>()
    private val callNumberExecutor = mock<CallNumberExecutor>()
    private val callNumberOnViberExecutor = mock<CallNumberOnViberExecutor>()

    val actionExecutorImpl = ActionExecutorImpl(
        shareExecutor,
        sendEmailExecutor,
        openPlayStoreExecutor,
        openUrlExecutor,
        callNumberExecutor,
        callNumberOnViberExecutor
    )

    @Test
    fun callShareExecutor_whenShareAction() {
        val action = Action.ShareAction("url")

        actionExecutorImpl.execute(action)

        verify(shareExecutor).invoke(action)
    }

    @Test
    fun callSendEmailExecutor_whenSendEmailAction() {
        val action = Action.SendEmailAction("email")

        actionExecutorImpl.execute(action)

        verify(sendEmailExecutor).invoke(action)
    }

    @Test
    fun callOpenPlayStoreExecutor_whenOpenPlayStoreAction() {
        val action = Action.OpenPlayStoreAction("application_id")

        actionExecutorImpl.execute(action)

        verify(openPlayStoreExecutor).invoke(action)
    }

    @Test
    fun callOpenUrlExecutor_whenOpenUrlAction() {
        val action = Action.OpenUrlAction("url")

        actionExecutorImpl.execute(action)

        verify(openUrlExecutor).invoke(action)
    }

    @Test
    fun callCallNumberExecutor_whenCallNumberAction() {
        val action = Action.CallNumberAction("phone_number")

        actionExecutorImpl.execute(action)

        verify(callNumberExecutor).invoke(action)
    }

    @Test
    fun callCallNumberOnViberExecutor_whenCallNumberOnViberAction() {
        val action = Action.CallNumberOnViberAction("phone_number")

        actionExecutorImpl.execute(action)

        verify(callNumberOnViberExecutor).invoke(action)
    }
}
