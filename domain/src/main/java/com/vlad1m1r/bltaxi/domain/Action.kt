package com.vlad1m1r.bltaxi.domain

sealed class Action {
    data class ShareAction(val url: String) : Action()
    data class OpenPlayStoreAction(val applicationId: String) : Action()
    data class OpenUrlAction(val url: String) : Action()
    data class SendEmailAction(val email: String) : Action()
    data class CallNumberAction(val phoneNumber: String) : Action()
    data class CallNumberOnViberAction(val phoneNumber: String) : Action()
}

interface ActionExecutor {
    fun execute(action: Action)
}