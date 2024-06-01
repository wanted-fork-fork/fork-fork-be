package com.fork.forkfork.auth.controller

import com.fork.forkfork.auth.dto.UserTokenDto
import com.fork.forkfork.auth.dto.request.RefreshTokenRequest
import com.fork.forkfork.auth.service.AuthService
import com.fork.forkfork.auth.service.TokenService
import com.fork.forkfork.auth.util.AuthUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService, val tokenService: TokenService) {
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Unit> {
        authService.logout(AuthUtil.getUserIdFromSecurityContext())
        return ResponseEntity.ok().build()
    }

    @GetMapping("/info")
    fun info(): ResponseEntity<String> {
        return ResponseEntity.ok().body("Hello, userId : ${AuthUtil.getUserIdFromSecurityContext()}")
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody refreshTokenRequest: RefreshTokenRequest,
    ): ResponseEntity<UserTokenDto> {
        return ResponseEntity.ok().body(tokenService.refreshToken(refreshTokenRequest.accessToken, refreshTokenRequest.refreshToken))
    }
}
