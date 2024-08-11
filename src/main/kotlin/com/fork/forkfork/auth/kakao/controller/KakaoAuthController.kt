package com.fork.forkfork.auth.kakao.controller

import com.fork.forkfork.auth.dto.response.AccessTokenResponse
import com.fork.forkfork.auth.kakao.service.KakaoAuthService
import com.fork.forkfork.auth.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/kakao")
class KakaoAuthController(val kakaoAuthService: KakaoAuthService, val authService: AuthService) {
    @GetMapping("/login")
    fun loginKakao(
        @RequestParam code: String,
        response: HttpServletResponse,
    ): ResponseEntity<AccessTokenResponse> {
        val loginInfoDto = kakaoAuthService.getKakaoUserInfo(code)
        val token = authService.login(loginInfoDto)
        return ResponseEntity.ok().body(token)
    }
}
