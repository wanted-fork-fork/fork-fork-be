package com.fork.forkfork.auth.kakao.controller

import com.fork.forkfork.auth.domain.enums.OAuthCompany
import com.fork.forkfork.auth.dto.LoginInfoDto
import com.fork.forkfork.auth.dto.response.AccessTokenResponse
import com.fork.forkfork.auth.kakao.service.KakaoAuthService
import com.fork.forkfork.auth.service.AuthService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(KakaoAuthController::class)
@WithMockUser(username = "test")
class KakaoAuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var kakaoAuthService: KakaoAuthService

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun `loginKakao는 token을 반환한다`() {
        val userTokenDto = AccessTokenResponse("accessToken")
        val code = "code"

        val loginInfoDto = LoginInfoDto("name", "profileImage", 1L, OAuthCompany.KAKAO)
        every { kakaoAuthService.getKakaoUserInfo(code) } returns loginInfoDto
        every { authService.login(loginInfoDto) } returns userTokenDto

        mockMvc.get("/api/v1/auth/kakao/login?code=$code")
            .andExpect {
                status { isOk() }
                content { json("""{"accessToken":"accessToken"}""") }
            }
    }
}
