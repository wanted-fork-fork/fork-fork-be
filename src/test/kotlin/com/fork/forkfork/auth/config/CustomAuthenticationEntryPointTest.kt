package com.fork.forkfork.auth.config

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.AuthenticationException

@ExtendWith(MockKExtension::class)
internal class CustomAuthenticationEntryPointTest {
    @Test
    fun`authenticationEntryPoint는 401 에러를 반환할 것이다`() {
        // Given
        val customAuthenticationEntryPoint = CustomAuthenticationEntryPoint()
        val request = mockk<HttpServletRequest>()
        val response = MockHttpServletResponse()
        val authException = mockk<AuthenticationException>()
        every { authException.message } returns "Unauthorized error: Unauthorized"

        // When
        customAuthenticationEntryPoint.commence(request, response, authException)

        // Then
        assertThat(response.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }
}
