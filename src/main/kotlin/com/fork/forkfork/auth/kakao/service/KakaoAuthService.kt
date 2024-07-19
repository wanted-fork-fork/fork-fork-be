package com.fork.forkfork.auth.kakao.service

import com.fork.forkfork.auth.domain.enums.OAuthCompany
import com.fork.forkfork.auth.dto.LoginInfoDto
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
    fun getKakaoUserInfo(code: String): LoginInfoDto {
        val tokenResponse = getAccessToken(code)
        val headers =
            mapOf(
                CONTENT_TYPE to APPLICATION_FORM_URLENCODED_VALUE,
                AUTHORIZATION to "${tokenResponse.tokenType} ${tokenResponse.accessToken}",
            )
        val kakaoUserInfo = kaKaoApiFeignClient.getKakaoUserInfo(headers)
        return LoginInfoDto(
            kakaoUserInfo.kakaoAccount.profile.nickname,
            kakaoUserInfo.kakaoAccount.profile.profileImageUrl,
            kakaoUserInfo.id,
            OAuthCompany.KAKAO,
        )
    }

    fun getAccessToken(code: String) =
        kaKaoAuthFeignClient.getAccessToken(
            restApiKey = kakaoAuthProperties.restApiKey,
            redirectUrl = kakaoAuthProperties.redirectUri,
            code = code,
            grantType = GRANT_TYPE,
        )

    companion object {
        private const val GRANT_TYPE = "authorization_code"
        private const val AUTHORIZATION_TYPE = "Bearer"
    }
}
