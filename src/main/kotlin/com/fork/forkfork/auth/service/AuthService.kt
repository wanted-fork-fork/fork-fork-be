package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import com.fork.forkfork.auth.dto.LoginInfoDto
import com.fork.forkfork.auth.dto.response.AccessTokenResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class AuthService(val userRepository: UserRepository, val tokenService: TokenService) {
    fun login(
        loginInfoDto: LoginInfoDto,
        response: HttpServletResponse,
    ): AccessTokenResponse {
        val user = getUser(loginInfoDto)
        val token = user.id?.let { tokenService.createToken(it) } ?: throw Exception("Not found user id")
        response.addCookie(tokenService.createCookie(token.refreshToken))
        return AccessTokenResponse(token.accessToken)
    }

    fun logout(userId: String) {
        tokenService.expireToken(userId)
    }

    fun getUser(loginInfoDto: LoginInfoDto): User {
        userRepository.findByOauthIdAndOauthCompany(loginInfoDto.oauthId, loginInfoDto.oauthCompany)?.let { return it }
        return saveUser(loginInfoDto)
    }

    fun saveUser(loginInfoDto: LoginInfoDto): User =
        userRepository.save(
            User(loginInfoDto.name, loginInfoDto.profileImage, loginInfoDto.oauthId, loginInfoDto.oauthCompany, OffsetDateTime.now()),
        )

    fun isExistUser(userId: String): Boolean = userRepository.existsById(userId)
}
