package com.fork.forkfork.auth.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class UserTokenDtoTest {
    @Test
    fun `UserTokenDto는 accessToken과 refreshToken 필드를 가진다`() {
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"

        val userTokenDto = UserTokenDto(accessToken, refreshToken)

        assertAll(
            { assertThat(userTokenDto.accessToken).isEqualTo(accessToken) },
            { assertThat(userTokenDto.refreshToken).isEqualTo(refreshToken) },
        )
    }
}
