package com.fork.forkfork.auth.kakao.dto.response

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KakaoLogoutResponseTest {
    @Test
    fun `KakaoLogoutResponse는 id 필드를 가진다`() {
        val id = 1L

        val kakaoLogoutResponse = KakaoLogoutResponse(id)

        assertThat(kakaoLogoutResponse.id).isEqualTo(id)
    }
}
