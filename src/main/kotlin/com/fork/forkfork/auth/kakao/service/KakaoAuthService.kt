package com.fork.forkfork.auth.kakao.service

import com.fork.forkfork.auth.kakao.dto.request.KakaoLogoutRequest
import com.fork.forkfork.auth.kakao.dto.response.KakaoUserInfoResponse
import com.fork.forkfork.auth.kakao.feign.KaKaoApiFeignClient
import com.fork.forkfork.auth.kakao.feign.KaKaoAuthFeignClient
import com.fork.forkfork.auth.kakao.properties.KakaoAuthProperties
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.stereotype.Service

@Service
class KakaoAuthService(
    val kaKaoAuthFeignClient: KaKaoAuthFeignClient,
    val kaKaoApiFeignClient: KaKaoApiFeignClient,
    val kakaoAuthProperties: KakaoAuthProperties,
) {
    fun getKakaoUserInfo(code: String): KakaoUserInfoResponse {
        val tokenResponse = getAccessToken(code)
        val headers =
            mapOf(
                CONTENT_TYPE to APPLICATION_FORM_URLENCODED_VALUE,
                AUTHORIZATION to "${tokenResponse.tokenType} ${tokenResponse.accessToken}",
            )
        return kaKaoApiFeignClient.getKakaoUserInfo(headers)
    }

    fun getAccessToken(code: String) =
        kaKaoAuthFeignClient.getAccessToken(
            restApiKey = kakaoAuthProperties.restApiKey,
            redirectUrl = kakaoAuthProperties.redirectUri,
            code = code,
            grantType = GRANT_TYPE,
        )

    fun logout(
        accessToken: String,
        targetKakaoId: Long,
    ) {
        val headers = mapOf(AUTHORIZATION to "$AUTHORIZATION_TYPE $accessToken")
        kaKaoApiFeignClient.logout(headers, KakaoLogoutRequest(targetKakaoId))
    }

    companion object {
        private const val GRANT_TYPE = "authorization_code"
        private const val AUTHORIZATION_TYPE = "Bearer"
    }
}
