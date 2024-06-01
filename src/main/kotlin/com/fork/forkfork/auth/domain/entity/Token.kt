package com.fork.forkfork.auth.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tokens")
class Token(
    @Id
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)
