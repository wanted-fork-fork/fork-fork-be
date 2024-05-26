package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun getUser(user: User): User {
        userRepository.findByOauthIdAndOauthCompany(user.oauthId, user.oauthCompany)?.let { return it }
        return saveUser(user)
    }

    private fun saveUser(user: User): User = userRepository.save(user)
}
