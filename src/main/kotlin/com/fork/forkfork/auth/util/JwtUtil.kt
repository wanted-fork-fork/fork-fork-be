package com.fork.forkfork.auth.util

import com.fork.forkfork.auth.domain.enums.TokenType
import com.fork.forkfork.auth.properties.JwtProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import java.security.Key
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.Date

@Component
class JwtUtil(private val jwtProperties: JwtProperties) {
    private val log = KotlinLogging.logger { }
    val key: Key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun createToken(
        userId: String,
        tokenType: TokenType,
    ): String =
        Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userId))
            .setExpiration(Date(System.currentTimeMillis() + getTokenExpirationTime(tokenType)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

    private fun getTokenExpirationTime(tokenType: TokenType): Long =
        when (tokenType) {
            TokenType.ACCESS -> jwtProperties.expirationTime
            TokenType.REFRESH -> jwtProperties.expirationTime * 5
        }

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info(e) { INVALID_JWT_TOKEN_ERROR_MESSAGE }
        } catch (e: MalformedJwtException) {
            log.info(e) { INVALID_JWT_TOKEN_ERROR_MESSAGE }
        } catch (e: ExpiredJwtException) {
            log.info(e) { EXPIRATION_TIME_ERROR_MESSAGE }
        } catch (e: UnsupportedJwtException) {
            log.info(e) { UNSUPPORTED_JWT_TOKEN_ERROR_MESSAGE }
        } catch (e: SignatureException) {
            log.info(e) { INVALID_JWT_TOKEN_ERROR_MESSAGE }
        }
        return false
    }

    fun getExpirationDate(token: String): OffsetDateTime {
        return OffsetDateTime.ofInstant(getClaims(token).expiration.toInstant(), ZoneId.systemDefault())
    }

    companion object {
        private const val INVALID_JWT_TOKEN_ERROR_MESSAGE = "Invalid JWT Token"
        private const val EXPIRATION_TIME_ERROR_MESSAGE = "Expired JWT Token"
        private const val UNSUPPORTED_JWT_TOKEN_ERROR_MESSAGE = "Unsupported JWT Token"
    }
}
