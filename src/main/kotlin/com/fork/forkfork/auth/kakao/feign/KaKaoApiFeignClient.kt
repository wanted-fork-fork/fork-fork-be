package com.fork.forkfork.auth.kakao.feign

import com.fork.forkfork.auth.kakao.dto.request.KakaoLogoutRequest
import com.fork.forkfork.auth.kakao.dto.response.KakaoLogoutResponse
import com.fork.forkfork.auth.kakao.dto.response.KakaoUserInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "kakao-api", url = "https://kapi.kakao.com")
interface KaKaoApiFeignClient {
    @GetMapping("/v2/user/me")
    fun getKakaoUserInfo(
        @RequestHeader headers: Map<String, String>,
    ): KakaoUserInfoResponse

    @PostMapping("/v1/user/logout")
    fun logout(
        @RequestHeader headers: Map<String, String>,
        @RequestBody request: KakaoLogoutRequest,
    ): KakaoLogoutResponse
}
