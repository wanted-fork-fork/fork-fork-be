package com.fork.forkfork.image.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "s3")
class S3KeyProperties(val accessKey: String, val secretKey: String, val bucket: String)
