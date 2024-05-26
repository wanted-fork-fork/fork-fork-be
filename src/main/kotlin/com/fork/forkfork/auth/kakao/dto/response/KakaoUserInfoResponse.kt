package com.fork.forkfork.auth.kakao.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class KakaoUserInfoResponse(
    val id: Long,
    @JsonProperty("connected_at")
    val connectedAt: String,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount,
) {
    class KakaoAccount(
        val profile: KakaoProfile,
    )

    class KakaoProfile(
        val nickname: String,
        @JsonProperty("thumbnail_image_url")
        val thumbnailImageUrl: String?,
        @JsonProperty("profile_image_url")
        val profileImageUrl: String?,
    )
}
