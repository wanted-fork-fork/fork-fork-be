package com.fork.forkfork.auth.kakao.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

class KakaoLogoutRequest(
    @JsonProperty("target_id")
    val targetId: Long,
    @JsonProperty("target_id_type")
    val targetIdType: String = "user_id"
)
