package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun createUser(user: User): User {
        userRepository.findByOauthIdAndOauthCompany(user.oauthId, user.oauthCompany)?.let { return it }
        return userRepository.save(user)
    }
}
