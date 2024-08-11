package com.fork.forkfork.auth.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class JwtPropertiesTest {
    @Test
    fun `JwtProperties는 expirationTime과 secret을 가진다`() {
        val jwtProperties =
            JwtProperties(accessExpirationTime = 1000, secret = "secret", refreshExpirationTime = 1000, refreshCookieTime = 1000)

        assertThat(jwtProperties.accessExpirationTime).isEqualTo(1000)
        assertThat(jwtProperties.refreshExpirationTime).isEqualTo(1000)
        assertThat(jwtProperties.refreshCookieTime).isEqualTo(1000)
        assertThat(jwtProperties.secret).isEqualTo("secret")
    }
}
