package com.vlad1m1r.bltaxi.about.ui

sealed interface AboutAction {
    object SendEmailClicked : AboutAction
    object RateAppClicked : AboutAction
    object ShareAppClicked : AboutAction
    object PrivacyPolicyClicked : AboutAction
    object TermsAndConditionsClicked : AboutAction
}
