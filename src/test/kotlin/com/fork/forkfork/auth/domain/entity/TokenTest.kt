package com.fork.forkfork.auth.domain.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TokenTest {
    @Test
    fun `token entity의 field는 모두 stirng이다`() {
        val userId = "userId"
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"

        val token = Token(userId, accessToken, refreshToken)

        assertThat(token.userId).isEqualTo(userId)
        assertThat(token.accessToken).isEqualTo(accessToken)
        assertThat(token.refreshToken).isEqualTo(refreshToken)
    }
}
