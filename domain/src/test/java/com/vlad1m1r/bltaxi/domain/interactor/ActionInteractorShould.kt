package com.vlad1m1r.bltaxi.domain.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vlad1m1r.bltaxi.domain.Action
import com.vlad1m1r.bltaxi.domain.ActionExecutor
import org.junit.Test

class ActionInteractorShould {

    val actionExecutor = mock<ActionExecutor>()
    val actionInteractor = ActionInteractor(actionExecutor)

    @Test
    fun callActionExecutor() {
        val action = mock<Action>()

        actionInteractor.execute(action)

        verify(actionExecutor).execute(action)
    }
}
