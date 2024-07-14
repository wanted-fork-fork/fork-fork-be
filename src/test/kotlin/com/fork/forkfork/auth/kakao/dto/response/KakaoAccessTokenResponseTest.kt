package com.fork.forkfork.auth.kakao.dto.response

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class KakaoAccessTokenResponseTest {
    @Test
    fun `KakaoAccessTokenResponse는 accessToken, tokenType, refreshToken, expiresIn, scope, refreshTokenExpiresIn 필드를 가진다`() {
        val accessToken = "accessToken"
        val tokenType = "tokenType"
        val refreshToken = "refreshToken"
        val expiresIn = 1
        val scope = "scope"
        val refreshTokenExpiresIn = 1

        val kakaoAccessTokenResponse =
            KakaoAccessTokenResponse(accessToken, tokenType, refreshToken, expiresIn, scope, refreshTokenExpiresIn)

        Assertions.assertThat(kakaoAccessTokenResponse.accessToken).isEqualTo(accessToken)
        Assertions.assertThat(kakaoAccessTokenResponse.tokenType).isEqualTo(tokenType)
        Assertions.assertThat(kakaoAccessTokenResponse.refreshToken).isEqualTo(refreshToken)
        Assertions.assertThat(kakaoAccessTokenResponse.expiresIn).isEqualTo(expiresIn)
        Assertions.assertThat(kakaoAccessTokenResponse.scope).isEqualTo(scope)
        Assertions.assertThat(kakaoAccessTokenResponse.refreshTokenExpiresIn).isEqualTo(refreshTokenExpiresIn)
    }
}
