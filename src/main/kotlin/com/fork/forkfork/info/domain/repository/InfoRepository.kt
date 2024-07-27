package com.fork.forkfork.info.domain.repository

import com.fork.forkfork.info.domain.entity.Info
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InfoRepository : MongoRepository<Info, String> {
    fun findAllByMatchMakerId(userId: String): List<Info>
}
