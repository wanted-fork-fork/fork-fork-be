package com.fork.forkfork.auth.util

import com.fork.forkfork.auth.properties.JwtProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtil(private val jwtProperties: JwtProperties) {
    private val log = KotlinLogging.logger { }
    val key: Key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun createToken(userId: Long): String =
        Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userId.toString()))
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.expirationTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

    fun getUserId(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info(e) { "Invalid JWT Token" }
        } catch (e: MalformedJwtException) {
            log.info(e) { "Invalid JWT Token" }
        } catch (e: ExpiredJwtException) {
            log.info(e) { "Expired JWT Token" }
        } catch (e: UnsupportedJwtException) {
            log.info(e) { "Unsupported JWT Token" }
        }
        return false
    }
}
