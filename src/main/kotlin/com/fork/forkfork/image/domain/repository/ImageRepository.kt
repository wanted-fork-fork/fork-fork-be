package com.fork.forkfork.image.domain.repository

import com.fork.forkfork.image.domain.entity.Image
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : MongoRepository<Image, String>
