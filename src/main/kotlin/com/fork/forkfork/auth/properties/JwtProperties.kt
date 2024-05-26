package com.fork.forkfork.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(val expirationTime: Long, val secret: String)
