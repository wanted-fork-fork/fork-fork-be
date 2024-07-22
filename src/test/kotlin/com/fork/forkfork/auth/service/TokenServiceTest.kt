package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.Token
import com.fork.forkfork.auth.domain.enums.TokenType
import com.fork.forkfork.auth.domain.repository.TokenRepository
import com.fork.forkfork.auth.util.JwtUtil
import com.fork.forkfork.exception.InvalidTokenException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TokenServiceTest {
    @InjectMockKs
    lateinit var tokenService: TokenService

    @MockK
    lateinit var tokenRepository: TokenRepository

    @MockK
    lateinit var jwtUtil: JwtUtil

    @Test
    fun `createToken은 2개의 토큰을 만들어서 repo에 저장한다`() {
        // given
        val userId = "1"
        every { jwtUtil.createToken(userId, any()) } returns "TOKEN"
        every { tokenRepository.save(any()) } returns Token(userId, "TOKEN", "TOKEN")

        // when
        tokenService.createToken(userId)

        // then
        verify(exactly = 1) { jwtUtil.createToken(userId, TokenType.ACCESS) }
        verify(exactly = 1) { jwtUtil.createToken(userId, TokenType.REFRESH) }
        verify(exactly = 1) { tokenRepository.save(any()) }
    }

    @Test
    fun `expireToken은 userId에 해당하는 토큰을 삭제한다`() {
        // given
        val userId = "1"
        every { tokenRepository.deleteByUserId(userId) } returns Unit

        // when
        tokenService.expireToken(userId)

        // then
        verify(exactly = 1) { tokenRepository.deleteByUserId(userId) }
    }

    @Test
    fun `refreshToken은 refreshToken이 유효하지 않으면 InvalidTokenException을 던진다`() {
        // given
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        every { jwtUtil.validateToken(refreshToken) } returns false

        // when then
        assertThrows<InvalidTokenException> {
            tokenService.refreshToken(accessToken, refreshToken)
        }
    }

    @Test
    fun `refreshToken은 refreshToken이 repo에 없으면 InvalidTokenException을 던진다`() {
        // given
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        every { jwtUtil.validateToken(refreshToken) } returns true
        every { tokenRepository.findByRefreshToken(refreshToken) } returns null

        // when then
        assertThrows<InvalidTokenException> {
            tokenService.refreshToken(accessToken, refreshToken)
        }
    }

    @Test
    fun `refreshToken이 repo와 다르다면 오류가 발생한다`() {
        // given
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        every { jwtUtil.validateToken(refreshToken) } returns true
        every { tokenRepository.findByRefreshToken(refreshToken) } returns Token("1", accessToken, refreshToken + "1")

        // when then
        assertThrows<InvalidTokenException> {
            tokenService.refreshToken(accessToken, refreshToken)
        }
    }

    @Test
    fun `accessToken이 repo와 다르다면 오류가 발생한다`() {
        // given
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        every { jwtUtil.validateToken(refreshToken) } returns true
        every { tokenRepository.findByRefreshToken(refreshToken) } returns Token("1", accessToken + "1", refreshToken)

        // when then
        assertThrows<InvalidTokenException> {
            tokenService.refreshToken(accessToken, refreshToken)
        }
    }
}
