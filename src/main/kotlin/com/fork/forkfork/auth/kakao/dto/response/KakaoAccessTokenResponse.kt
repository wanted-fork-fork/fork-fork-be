package com.fork.forkfork.auth.kakao.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class KakaoAccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    val scope: String,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int
)
