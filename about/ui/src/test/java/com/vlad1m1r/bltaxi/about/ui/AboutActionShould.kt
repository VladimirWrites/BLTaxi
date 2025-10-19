package com.vlad1m1r.bltaxi.about.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AboutActionShould {

    @Test
    fun `create SendEmailClicked action`() {
        val action = AboutAction.SendEmailClicked
        assertThat(action).isInstanceOf(AboutAction::class.java)
    }

    @Test
    fun `create RateAppClicked action`() {
        val action = AboutAction.RateAppClicked
        assertThat(action).isInstanceOf(AboutAction::class.java)
    }

    @Test
    fun `create ShareAppClicked action`() {
        val action = AboutAction.ShareAppClicked
        assertThat(action).isInstanceOf(AboutAction::class.java)
    }

    @Test
    fun `create PrivacyPolicyClicked action`() {
        val action = AboutAction.PrivacyPolicyClicked
        assertThat(action).isInstanceOf(AboutAction::class.java)
    }

    @Test
    fun `create TermsAndConditionsClicked action`() {
        val action = AboutAction.TermsAndConditionsClicked
        assertThat(action).isInstanceOf(AboutAction::class.java)
    }

    @Test
    fun `action types are different`() {
        assertThat(AboutAction.SendEmailClicked).isNotEqualTo(AboutAction.RateAppClicked)
        assertThat(AboutAction.ShareAppClicked).isNotEqualTo(AboutAction.PrivacyPolicyClicked)
        assertThat(AboutAction.TermsAndConditionsClicked).isNotEqualTo(AboutAction.SendEmailClicked)
    }
}
