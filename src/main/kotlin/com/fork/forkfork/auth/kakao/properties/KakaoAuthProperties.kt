package com.fork.forkfork.auth.kakao.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.auth")
class KakaoAuthProperties(val restApiKey: String, val redirectUri: String)
