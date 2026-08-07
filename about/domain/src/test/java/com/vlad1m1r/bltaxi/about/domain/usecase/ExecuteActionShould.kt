package com.vlad1m1r.bltaxi.about.domain.usecase

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.ActionExecutor
import org.junit.Test

class ExecuteActionShould {

    private val actionExecutor = mock<ActionExecutor>()
    private val executeAction = ExecuteAction(actionExecutor)

    @Test
    fun callActionExecutor() {
        val action = Action.OpenUrlAction("https://vladimirj.com")

        executeAction(action)

        verify(actionExecutor).execute(action)
    }
}
