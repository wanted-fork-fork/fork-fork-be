package com.fork.forkfork.sharing.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sharing")
class SharingProperties(val expirationHours: Long)
