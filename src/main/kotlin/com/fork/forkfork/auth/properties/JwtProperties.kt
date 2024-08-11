package com.fork.forkfork.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val accessExpirationTime: Long,
    val refreshExpirationTime: Long,
    val refreshCookieTime: Int,
    val secret: String,
)
