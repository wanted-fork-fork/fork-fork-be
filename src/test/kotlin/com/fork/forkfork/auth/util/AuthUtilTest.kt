package com.fork.forkfork.auth.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

internal class AuthUtilTest {
    @Test
    fun `getUserIdFromSecurityContext는 SecurityContextHolder에서 userId를 가져온다`() {
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken("userId", null, emptyList())

        assertThat(AuthUtil.getUserIdFromSecurityContext()).isEqualTo("userId")
    }
}
