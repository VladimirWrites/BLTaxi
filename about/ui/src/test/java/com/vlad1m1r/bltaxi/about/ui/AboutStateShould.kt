package com.vlad1m1r.bltaxi.about.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AboutStateShould {

    @Test
    fun `create state with default values`() {
        val state = AboutState()

        assertThat(state.appVersion).isEmpty()
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `create state with custom values`() {
        val state = AboutState(
            appVersion = "1.2.3",
            isLoading = true
        )

        assertThat(state.appVersion).isEqualTo("1.2.3")
        assertThat(state.isLoading).isTrue()
    }

    @Test
    fun `copy state with new version`() {
        val originalState = AboutState(appVersion = "1.0.0")
        val newState = originalState.copy(appVersion = "2.0.0")

        assertThat(originalState.appVersion).isEqualTo("1.0.0")
        assertThat(newState.appVersion).isEqualTo("2.0.0")
        assertThat(newState.isLoading).isEqualTo(originalState.isLoading)
    }

    @Test
    fun `copy state with loading flag`() {
        val originalState = AboutState(isLoading = false)
        val newState = originalState.copy(isLoading = true)

        assertThat(originalState.isLoading).isFalse()
        assertThat(newState.isLoading).isTrue()
        assertThat(newState.appVersion).isEqualTo(originalState.appVersion)
    }

    @Test
    fun `states with same values are equal`() {
        val state1 = AboutState(appVersion = "1.0.0", isLoading = false)
        val state2 = AboutState(appVersion = "1.0.0", isLoading = false)

        assertThat(state1).isEqualTo(state2)
    }

    @Test
    fun `states with different values are not equal`() {
        val state1 = AboutState(appVersion = "1.0.0")
        val state2 = AboutState(appVersion = "2.0.0")

        assertThat(state1).isNotEqualTo(state2)
    }
}
