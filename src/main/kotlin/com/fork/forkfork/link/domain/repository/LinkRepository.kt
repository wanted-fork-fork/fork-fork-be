package com.fork.forkfork.link.domain.repository

import com.fork.forkfork.link.domain.entity.Link
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface LinkRepository : MongoRepository<Link, String> {
    fun existsByKey(key: String): Boolean

    fun findByMatchMakerId(matchMakerId: String): Optional<Link>
}
