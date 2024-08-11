package com.fork.forkfork.auth.dto.request

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RefreshTokenRequestTest {
    @Test
    fun `RefreshTokenRequest는 accessToken과 refreshToken 필드를 가진다`() {
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"

        val refreshTokenRequest = RefreshTokenRequest(accessToken)

        assertAll(
            { Assertions.assertThat(refreshTokenRequest.accessToken).isEqualTo(accessToken) },
        )
    }
}
