package com.fork.forkfork.auth.dto

import com.fork.forkfork.auth.domain.enums.OAuthCompany
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class LoginInfoDtoTest {
    @Test
    fun `LoginInfoDto는 name, profileImage, oauthId, oauthCompany 필드를 가진다`() {
        val name = "name"
        val profileImage = "profileImage"
        val oauthId = 1L
        val oauthCompany = OAuthCompany.KAKAO

        val loginInfoDto = LoginInfoDto(name, profileImage, oauthId, oauthCompany)

        assertAll(
            { assertThat(loginInfoDto.name).isEqualTo(name) },
            { assertThat(loginInfoDto.profileImage).isEqualTo(profileImage) },
            { assertThat(loginInfoDto.oauthId).isEqualTo(oauthId) },
            { assertThat(loginInfoDto.oauthCompany).isEqualTo(oauthCompany) },
        )
    }

    @Test
    fun `profileImage가 null인 경우 LoginInfoDto는 name, oauthId, oauthCompany 필드를 가진다`() {
        val name = "name"
        val oauthId = 1L
        val oauthCompany = OAuthCompany.KAKAO

        val loginInfoDto = LoginInfoDto(name, null, oauthId, oauthCompany)

        assertAll(
            { assertThat(loginInfoDto.name).isEqualTo(name) },
            { assertThat(loginInfoDto.profileImage).isNull() },
            { assertThat(loginInfoDto.oauthId).isEqualTo(oauthId) },
            { assertThat(loginInfoDto.oauthCompany).isEqualTo(oauthCompany) },
        )
    }
}
