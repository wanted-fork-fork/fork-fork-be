package com.fork.forkfork.auth.domain.repository

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.enums.OAuthCompany
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, String?> {
    fun findByOauthIdAndOauthCompany(oauthId: Long, oauthCompany: OAuthCompany): User?
}
