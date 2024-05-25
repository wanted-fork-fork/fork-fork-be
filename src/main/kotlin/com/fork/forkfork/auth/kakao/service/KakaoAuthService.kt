package com.fork.forkfork.auth.kakao.service

import com.fork.forkfork.auth.kakao.feign.KaKaoAuthFeignClient
import com.fork.forkfork.auth.kakao.properties.KakaoAuthProperties
import org.springframework.stereotype.Service

@Service
class KakaoAuthService(val kaKaoAuthFeignClient: KaKaoAuthFeignClient, val kakaoAuthProperties: KakaoAuthProperties) {
    fun getAccessToken(code: String): String {
        val tokenResponse = kaKaoAuthFeignClient.getAccessToken(
            restApiKey = kakaoAuthProperties.restApiKey,
            redirectUrl = kakaoAuthProperties.redirectUri,
            code = code,
            grantType = "authorization_code"
        )
        return tokenResponse.accessToken
    }
}
