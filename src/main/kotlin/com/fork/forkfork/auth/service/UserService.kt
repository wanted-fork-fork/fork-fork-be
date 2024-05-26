package com.fork.forkfork.auth.service

import com.fork.forkfork.auth.domain.entity.User
import com.fork.forkfork.auth.domain.repository.UserRepository
import com.fork.forkfork.auth.dto.LoginInfoDto
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val tokenService: TokenService) {
    fun login(loginInfoDto: LoginInfoDto): String {
        val user = getUser(User(loginInfoDto.name, loginInfoDto.profileImage, loginInfoDto.oauthId, loginInfoDto.oauthCompany))
        return user.id?.let { tokenService.createToken(it) } ?: throw Exception("Not found user id")
    }

    fun getUser(user: User): User {
        userRepository.findByOauthIdAndOauthCompany(user.oauthId, user.oauthCompany)?.let { return it }
        return saveUser(user)
    }

    fun saveUser(user: User): User = userRepository.save(user)
}
