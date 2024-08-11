package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import com.fork.forkfork.auth.dto.LoginInfoDto
import com.fork.forkfork.auth.dto.response.AccessTokenResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class AuthService(val userRepository: UserRepository, val tokenService: TokenService) {
    fun login(
        loginInfoDto: LoginInfoDto,
        response: HttpServletResponse,
    ): AccessTokenResponse {
        val user = getUser(User(loginInfoDto.name, loginInfoDto.profileImage, loginInfoDto.oauthId, loginInfoDto.oauthCompany))
        val token = user.id?.let { tokenService.createToken(it) } ?: throw Exception("Not found user id")
        response.addCookie(tokenService.createCookie(token.refreshToken))
        return AccessTokenResponse(token.accessToken)
    }

    fun logout(userId: String) {
        tokenService.expireToken(userId)
    }

    fun getUser(user: User): User {
        userRepository.findByOauthIdAndOauthCompany(user.oauthId, user.oauthCompany)?.let { return it }
        return saveUser(user)
    }

    fun saveUser(user: User): User = userRepository.save(user)

    fun isExistUser(userId: String): Boolean = userRepository.existsById(userId)
}
