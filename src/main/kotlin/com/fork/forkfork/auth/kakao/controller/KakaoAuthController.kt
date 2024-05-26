package com.fork.forkfork.auth.kakao.controller

import com.fork.forkfork.auth.dto.UserTokenDto
import com.fork.forkfork.auth.kakao.service.KakaoAuthService
import com.fork.forkfork.auth.service.TokenService
import com.fork.forkfork.auth.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/kakao")
class KakaoAuthController(val kakaoAuthService: KakaoAuthService, val userService: UserService, val tokenService: TokenService) {
    @GetMapping("/login")
    fun loginKakao(
        @RequestParam code: String,
    ): ResponseEntity<UserTokenDto> {
        val loginInfoDto = kakaoAuthService.getKakaoUserInfo(code)
        val token = userService.login(loginInfoDto)
        return ResponseEntity.ok().body(token)
    }
}
