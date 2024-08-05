package com.fork.forkfork.sharing.domain.repository

import com.fork.forkfork.sharing.domain.entity.Sharing
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SharingRepository : MongoRepository<Sharing, String>
