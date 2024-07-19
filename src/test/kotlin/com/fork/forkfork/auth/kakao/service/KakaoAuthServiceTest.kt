package com.fork.forkfork.auth.kakao.service

import com.fork.forkfork.auth.kakao.dto.response.KakaoAccessTokenResponse
import com.fork.forkfork.auth.kakao.dto.response.KakaoUserInfoResponse
import com.fork.forkfork.auth.kakao.feign.KaKaoApiFeignClient
import com.fork.forkfork.auth.kakao.feign.KaKaoAuthFeignClient
import com.fork.forkfork.auth.kakao.properties.KakaoAuthProperties
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class KakaoAuthServiceTest {
    @InjectMockKs
    private lateinit var kakaoAuthService: KakaoAuthService

    @MockK
    private lateinit var kaKaoAuthFeignClient: KaKaoAuthFeignClient

    @MockK
    private lateinit var kaKaoApiFeignClient: KaKaoApiFeignClient
    val kakaoAuthProperties: KakaoAuthProperties = KakaoAuthProperties("restApiKey", "redirectUri")

    @Test
    fun `kakao 로그인 시 kakao_getAccessToken와 kakao_getKakaoUserInfo를 호출한다`() {
        val code = "code"
        every {
            kaKaoAuthFeignClient.getAccessToken(any(), any(), any(), any())
        } returns KakaoAccessTokenResponse("tokenType", "accessToken", "refreshToken", 1000, "scope", 1000)
        every { kaKaoApiFeignClient.getKakaoUserInfo(any()) } returns
            KakaoUserInfoResponse(
                1L, "000000", KakaoUserInfoResponse.KakaoAccount(KakaoUserInfoResponse.KakaoProfile("nickname", "thumbnail", "profile")),
            )

        kakaoAuthService.getKakaoUserInfo(code)

        verify(exactly = 1) { kaKaoAuthFeignClient.getAccessToken(any(), any(), any(), any()) }
        verify(exactly = 1) { kaKaoApiFeignClient.getKakaoUserInfo(any()) }
    }
}
