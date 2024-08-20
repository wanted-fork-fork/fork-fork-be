package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import com.fork.forkfork.auth.dto.LoginInfoDto
import com.fork.forkfork.auth.dto.response.AccessTokenResponse
import com.fork.forkfork.auth.dto.response.UserInfoResponse
import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class AuthService(val userRepository: UserRepository, val tokenService: TokenService) {
    fun login(
        loginInfoDto: LoginInfoDto,
        response: HttpServletResponse,
    ): AccessTokenResponse {
        val user = getOrSaveUser(loginInfoDto)
        val token = user.id?.let { tokenService.createToken(it) } ?: throw IllegalArgumentException("Not found user id")
        response.addCookie(tokenService.createCookie(token.refreshToken))
        return AccessTokenResponse(token.accessToken)
    }

    fun logout(userId: String) {
        tokenService.expireToken(userId)
    }

    fun getOrSaveUser(loginInfoDto: LoginInfoDto): User {
        userRepository.findByOauthIdAndOauthCompany(loginInfoDto.oauthId, loginInfoDto.oauthCompany)?.let { return it }
        return saveUser(loginInfoDto)
    }

    fun saveUser(loginInfoDto: LoginInfoDto): User =
        userRepository.save(
            User(loginInfoDto.name, loginInfoDto.profileImage, loginInfoDto.oauthId, loginInfoDto.oauthCompany, OffsetDateTime.now()),
        )

    fun isExistUser(userId: String): Boolean = userRepository.existsById(userId)

    fun getUserInfo(): UserInfoResponse {
        val userId = getUserIdFromSecurityContext()
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("Not found user. userId: $userId") }
        return UserInfoResponse(userId, user.name, user.profileImage)
    }
}
