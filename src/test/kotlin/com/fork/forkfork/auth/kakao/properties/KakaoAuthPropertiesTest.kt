package com.fork.forkfork.auth.kakao.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class KakaoAuthPropertiesTest {
    @Test
    fun `KakaoAuthProperties는 redirectUri, logoutRedirectUri 필드를 가진다`() {
        val redirectUri = "redirectUri"
        val restApiKey = "restApiKey"

        val kakaoAuthProperties = KakaoAuthProperties(restApiKey, redirectUri)

        assertAll(
            { assertThat(kakaoAuthProperties.redirectUri).isEqualTo(redirectUri) },
            { assertThat(kakaoAuthProperties.restApiKey).isEqualTo(restApiKey) },
        )
    }
}
