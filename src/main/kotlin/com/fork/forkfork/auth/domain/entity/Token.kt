package com.fork.forkfork.auth.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tokens")
class Token(
    @Id
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
): BaseAuditor()
