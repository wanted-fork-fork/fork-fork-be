package com.fork.forkfork.auth.controller

import com.fork.forkfork.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {
    @PostMapping("/logout")
    fun logout() {
        authService.logout("TEST") // TODO
    }
}
