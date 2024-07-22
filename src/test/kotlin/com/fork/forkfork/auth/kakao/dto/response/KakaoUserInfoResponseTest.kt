package com.fork.forkfork.auth.kakao.dto.response

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class KakaoUserInfoResponseTest {
    @Test
    fun `KakaoUserInfoResponse는 id, connectedAt, properties, kakaoAccount 필드를 가진다`() {
        val id = 1L
        val connectedAt = "connectedAt"
        val kakaoProfile = KakaoUserInfoResponse.KakaoProfile("nickname", "thumbnailImageURL", "profileImageURL")
        val kakaoAccount = KakaoUserInfoResponse.KakaoAccount(kakaoProfile)

        val kakaoUserInfoResponse = KakaoUserInfoResponse(id, connectedAt, kakaoAccount)

        Assertions.assertThat(kakaoUserInfoResponse.id).isEqualTo(id)
        Assertions.assertThat(kakaoUserInfoResponse.connectedAt).isEqualTo(connectedAt)
        Assertions.assertThat(kakaoUserInfoResponse.kakaoAccount).isEqualTo(kakaoAccount)
    }
}
