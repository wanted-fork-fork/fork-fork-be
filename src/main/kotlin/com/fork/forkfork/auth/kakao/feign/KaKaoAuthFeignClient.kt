package com.fork.forkfork.auth.kakao.feign

import com.fork.forkfork.auth.kakao.dto.response.KakaoAccessTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakao-auth", url = "https://kauth.kakao.com")
fun interface KaKaoAuthFeignClient {
    @PostMapping("/oauth/token")
    fun getAccessToken(
        @RequestParam("client_id") restApiKey: String,
        @RequestParam("redirect_uri") redirectUrl: String,
        @RequestParam("code") code: String,
        @RequestParam("grant_type") grantType: String
    ): KakaoAccessTokenResponse
}
