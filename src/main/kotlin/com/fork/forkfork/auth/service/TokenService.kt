package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.Token
import com.fork.forkfork.auth.domain.enums.TokenType
import com.fork.forkfork.auth.domain.repository.TokenRepository
import com.fork.forkfork.auth.dto.UserTokenDto
import com.fork.forkfork.auth.util.JwtUtil
import org.springframework.stereotype.Service

@Service
class TokenService(val tokenRepository: TokenRepository, val jwtUtil: JwtUtil) {
    fun createToken(userId: String): UserTokenDto {
        val accessToken = jwtUtil.createToken(userId, TokenType.ACCESS)
        val refreshToken = jwtUtil.createToken(userId, TokenType.REFRESH)
        tokenRepository.save(Token(userId, accessToken, refreshToken, jwtUtil.getExpirationDate(refreshToken)))
        return UserTokenDto(accessToken, refreshToken)
    }

    fun expireToken(userId: String) {
        tokenRepository.deleteByUserId(userId)
    }
}
