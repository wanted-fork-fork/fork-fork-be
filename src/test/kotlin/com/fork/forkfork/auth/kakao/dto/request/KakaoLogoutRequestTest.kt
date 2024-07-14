package com.fork.forkfork.auth.kakao.dto.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class KakaoLogoutRequestTest {
    @Test
    fun `KakaoLogoutRequest는 targetId와 targetIdType 필드를 가진다`() {
        val targetId = 1L
        val targetIdType = "user_id"

        val kakaoLogoutRequest = KakaoLogoutRequest(targetId, targetIdType)

        assertAll(
            { assertThat(kakaoLogoutRequest.targetId).isEqualTo(targetId) },
            { assertThat(kakaoLogoutRequest.targetIdType).isEqualTo(targetIdType) },
        )
    }
}
