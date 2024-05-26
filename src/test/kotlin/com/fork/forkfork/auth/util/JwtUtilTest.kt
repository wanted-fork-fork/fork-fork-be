package com.fork.forkfork.auth.util

import com.fork.forkfork.auth.properties.JwtProperties
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class JwtUtilTest {
    @Test
    fun `토큰 생성 및 파싱 테스트`() {
        // given
        val jwtUtil = getJwtUtil(EXPIRATION_TIME)

        // when
        val token = jwtUtil.createToken(USER_ID)
        val parsedUserId = jwtUtil.getUserId(token)

        // then
        Assertions.assertThat(parsedUserId).isEqualTo(USER_ID.toString())
    }

    @Test
    fun `토큰 유효성 검사 성공 테스트`() {
        // given
        val jwtUtil = getJwtUtil(EXPIRATION_TIME)

        // when
        val token = jwtUtil.createToken(USER_ID)
        val isValid = jwtUtil.validateToken(token)

        // then
        Assertions.assertThat(isValid).isTrue()
    }

    @Test
    fun `토큰 유효성 검사 실패 테스트 - invalid token`() {
        // given
        val jwtUtil = getJwtUtil(EXPIRATION_TIME)

        // when
        val token = jwtUtil.createToken(USER_ID)
        val isValid = jwtUtil.validateToken(token + "invalid")

        // then
        Assertions.assertThat(isValid).isFalse()
    }

    @Test
    fun `토큰 유효성 검사 실패 테스트 - expired token`() {
        // given
        val jwtUtil = getJwtUtil(0)

        // when
        val token = jwtUtil.createToken(USER_ID)
        val isValid = jwtUtil.validateToken(token)

        // then
        Assertions.assertThat(isValid).isFalse()
    }

    private fun getJwtUtil(expirationTime: Long) = JwtUtil(JwtProperties(expirationTime, SECRET))

    companion object {
        private const val EXPIRATION_TIME = 1000 * 60L
        private const val USER_ID = 1L
        private const val SECRET = "Yzk4YWFiMWEtNjc2My00MWYwLWJhY2YtZWMyYTI3NTFhNjU1"
    }
}
