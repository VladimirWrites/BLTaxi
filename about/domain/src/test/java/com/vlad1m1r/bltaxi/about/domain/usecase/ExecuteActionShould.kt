package com.vlad1m1r.bltaxi.about.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.bltaxi.about.domain.Action
import com.vlad1m1r.bltaxi.about.domain.ActionExecutor
import org.junit.Test

class ExecuteActionShould {

    private val actionExecutor = mock<ActionExecutor>()
    private val executeAction = ExecuteAction(actionExecutor)

    @Test
    fun callActionExecutor() {
        val action = mock<Action>()

        executeAction(action)

        verify(actionExecutor).execute(action)
    }
}
