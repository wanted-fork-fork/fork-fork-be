package com.fork.forkfork.auth.domain.repository

import com.fork.forkfork.auth.domain.entity.Token
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : MongoRepository<Token, String?> {
    fun deleteByUserId(userId: String)

    fun findByRefreshToken(refreshToken: String): Token?
}
