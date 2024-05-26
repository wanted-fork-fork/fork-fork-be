package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.enums.OAuthCompany
import com.fork.forkfork.auth.domain.repository.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class UserServiceTest {
    @InjectMockKs
    private lateinit var userService: UserService

    @MockK
    private lateinit var userRepository: UserRepository

    @Test
    fun `사용자를 초기 생성할 때 save를 호출한다`() {
        // given
        every { userRepository.findByOauthIdAndOauthCompany(any(), any()) } returns null
        every { userRepository.save(any()) } returns User("test", "test", 1, OAuthCompany.KAKAO)
        val user = User("test", "test", 1, OAuthCompany.KAKAO)

        // when
        userService.createUser(user)

        // then
        verify(exactly = 1) { userRepository.findByOauthIdAndOauthCompany(any(), any()) }
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `사용자가 이미 존재할 때 save를 호출하지 않는다`() {
        // given
        every { userRepository.findByOauthIdAndOauthCompany(any(), any()) } returns User("test", "test", 1, OAuthCompany.KAKAO)
        val user = User("test", "test", 1, OAuthCompany.KAKAO)

        // when
        userService.createUser(user)

        // then
        verify(exactly = 1) { userRepository.findByOauthIdAndOauthCompany(any(), any()) }
        verify(exactly = 0) { userRepository.save(any()) }
    }
}
