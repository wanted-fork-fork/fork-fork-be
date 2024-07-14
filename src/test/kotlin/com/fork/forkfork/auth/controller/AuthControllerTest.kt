package com.fork.forkfork.auth.controller

import com.fork.forkfork.auth.service.AuthService
import com.fork.forkfork.auth.service.TokenService
import com.fork.forkfork.auth.util.AuthUtil
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockKExtension::class)
internal class AuthControllerTest {
    @InjectMockKs
    private lateinit var authController: AuthController

    @MockK
    private lateinit var authService: AuthService

    @MockK
    private lateinit var tokenService: TokenService

    @MockK
    private lateinit var authUtil: AuthUtil

    @Test
    fun `logout은 userId를 받아서 authService의 logout을 호출할 것이다`() {
        // Given
        val userId = "userId"
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
        every { authService.logout(userId) } returns Unit

        // When
        authController.logout()

        // Then
        verify(exactly = 1) { authService.logout(userId) }
    }
}
