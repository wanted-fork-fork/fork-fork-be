package com.fork.forkfork.auth.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class JwtPropertiesTest {
    @Test
    fun `JwtProperties는 expirationTime과 secret을 가진다`() {
        val jwtProperties = JwtProperties(expirationTime = 1000, secret = "secret")

        assertThat(jwtProperties.expirationTime).isEqualTo(1000)
        assertThat(jwtProperties.secret).isEqualTo("secret")
    }
}
