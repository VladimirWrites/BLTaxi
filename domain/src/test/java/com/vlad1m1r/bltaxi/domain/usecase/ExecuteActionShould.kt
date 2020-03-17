package com.vlad1m1r.bltaxi.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import org.junit.Test

class ExecuteActionShould {

    val actionExecutor = mock<ActionExecutor>()
    val executeAction = ExecuteAction(actionExecutor)

    @Test
    fun callActionExecutor() {
        val action = mock<Action>()

        executeAction(action)

        verify(actionExecutor).execute(action)
    }
}
