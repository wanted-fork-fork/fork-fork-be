package com.fork.forkfork.config.filter

import com.fork.forkfork.auth.util.JwtUtil
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import jakarta.servlet.FilterChain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockKExtension::class)
internal class JwtAuthFilterTest {
    @MockK
    lateinit var jwtUtil: JwtUtil

    @MockK
    lateinit var filterChain: FilterChain

    @BeforeEach
    fun setUp() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `request에 Bearer 토큰이 없으면 인증되지 않는다`() {
        val jwtAuthFilter = JwtAuthFilter(jwtUtil)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        every { filterChain.doFilter(request, response) } returns Unit

        jwtAuthFilter.doFilter(request, response, filterChain)

        assertThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `token이 valid하지 않다면 인증되지 않는다`() {
        val jwtAuthFilter = JwtAuthFilter(jwtUtil)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        request.addHeader(AUTHORIZATION, "Bearer token")
        every { jwtUtil.validateToken("token") } returns false
        every { filterChain.doFilter(request, response) } returns Unit

        jwtAuthFilter.doFilter(request, response, filterChain)

        assertThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `token이 valid하다면 인증된다`() {
        val jwtAuthFilter = JwtAuthFilter(jwtUtil)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        request.addHeader(AUTHORIZATION, "Bearer token")
        every { jwtUtil.validateToken("token") } returns true
        every { jwtUtil.getClaims("token") } returns
            mockk {
                every { subject } returns "userId"
            }
        every { filterChain.doFilter(request, response) } returns Unit

        jwtAuthFilter.doFilter(request, response, filterChain)

        assertThat(SecurityContextHolder.getContext().authentication.principal).isEqualTo("userId")
    }
}
