package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.Token
import com.fork.forkfork.auth.domain.enums.TokenType
import com.fork.forkfork.auth.domain.repository.TokenRepository
import com.fork.forkfork.auth.dto.UserTokenDto
import com.fork.forkfork.auth.util.JwtUtil
import com.fork.forkfork.exception.InvalidTokenException
import org.springframework.stereotype.Service

@Service
class TokenService(val tokenRepository: TokenRepository, val jwtUtil: JwtUtil) {
    fun createToken(userId: String): UserTokenDto {
        val accessToken = jwtUtil.createToken(userId, TokenType.ACCESS)
        val refreshToken = jwtUtil.createToken(userId, TokenType.REFRESH)
        tokenRepository.save(Token(userId, accessToken, refreshToken))
        return UserTokenDto(accessToken, refreshToken)
    }

    fun expireToken(userId: String) {
        tokenRepository.deleteByUserId(userId)
    }

    fun refreshToken(
        accessToken: String,
        refreshToken: String,
    ): UserTokenDto {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE)
        }
        val token = tokenRepository.findByRefreshToken(refreshToken) ?: throw InvalidTokenException(TOKEN_NOT_FOUND_ERROR_MESSAGE)
        if (token.accessToken != accessToken || token.refreshToken != refreshToken) {
            throw InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE)
        }

        val newToken = createToken(token.userId)
        return UserTokenDto(newToken.accessToken, newToken.refreshToken)
    }

    companion object {
        private const val INVALID_TOKEN_ERROR_MESSAGE = "Invalid token"
        private const val TOKEN_NOT_FOUND_ERROR_MESSAGE = "Token not found"
    }
}
