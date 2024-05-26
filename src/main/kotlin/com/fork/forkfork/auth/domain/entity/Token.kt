package com.fork.forkfork.auth.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.OffsetDateTime

@Document("tokens")
class Token(
    @Id
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: OffsetDateTime,
)
