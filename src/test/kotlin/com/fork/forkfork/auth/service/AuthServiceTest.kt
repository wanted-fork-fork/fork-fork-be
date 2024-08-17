package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.enums.OAuthCompany
import com.fork.forkfork.auth.domain.repository.UserRepository
import com.fork.forkfork.auth.dto.LoginInfoDto
import com.fork.forkfork.auth.dto.UserTokenDto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockHttpServletResponse
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
internal class AuthServiceTest {
    @InjectMockKs
    private lateinit var authService: AuthService

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var tokenService: TokenService

    @Test
    fun `사용자를 초기 생성할 때 save를 호출한다`() {
        // given
        every { userRepository.findByOauthIdAndOauthCompany(any(), any()) } returns null
        every { userRepository.save(any()) } returns User("test", "test", 1, OAuthCompany.KAKAO, OffsetDateTime.now())
        val user = LoginInfoDto("test", "test", 1, OAuthCompany.KAKAO)

        // when
        authService.getUser(user)

        // then
        verify(exactly = 1) { userRepository.findByOauthIdAndOauthCompany(any(), any()) }
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `사용자가 이미 존재할 때 save를 호출하지 않는다`() {
        // given
        every {
            userRepository.findByOauthIdAndOauthCompany(any(), any())
        } returns User("test", "test", 1, OAuthCompany.KAKAO, OffsetDateTime.now())
        val user = LoginInfoDto("test", "test", 1, OAuthCompany.KAKAO)

        // when
        authService.getUser(user)

        // then
        verify(exactly = 1) { userRepository.findByOauthIdAndOauthCompany(any(), any()) }
        verify(exactly = 0) { userRepository.save(any()) }
    }

    @Test
    fun `logout 시 tokenService의 expireToken을 호출한다`() {
        // given
        val userId = "1"
        every { tokenService.expireToken(any()) } returns Unit

        // when
        authService.logout(userId)

        // then
        verify(exactly = 1) { tokenService.expireToken(userId) }
    }

    @Test
    fun `login시 user가 존재하면 tokenService의 createToken을 호출한다`() {
        // given
        val loginInfoDto = LoginInfoDto("test", "test", 1, OAuthCompany.KAKAO)
        val response = MockHttpServletResponse()
        every {
            userRepository.findByOauthIdAndOauthCompany(any(), any())
        } returns User("test", "test", 1, OAuthCompany.KAKAO, OffsetDateTime.now(), "1")
        every { tokenService.createToken(any()) } returns UserTokenDto("accessToken", "refreshToken")
        every { tokenService.createCookie(any()) } returns Cookie("refreshToken", "refreshToken")

        // when
        authService.login(loginInfoDto, response)

        // then
        verify(exactly = 1) { tokenService.createToken(any()) }
    }

    @Test
    fun `login시 user의 id가 존재하지 않으면 오류가 발생한다`() {
        // given
        val loginInfoDto = LoginInfoDto("test", "test", 1, OAuthCompany.KAKAO)
        val response = MockHttpServletResponse()
        every {
            userRepository.findByOauthIdAndOauthCompany(any(), any())
        } returns User("test", "test", 1, OAuthCompany.KAKAO, OffsetDateTime.now())

        // when
        assertThrows<Exception> {
            authService.login(loginInfoDto, response)
        }
    }
}
