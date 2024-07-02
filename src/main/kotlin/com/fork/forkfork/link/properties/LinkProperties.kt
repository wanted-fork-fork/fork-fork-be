package com.fork.forkfork.link.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "link")
class LinkProperties(val expirationHours: Long)
