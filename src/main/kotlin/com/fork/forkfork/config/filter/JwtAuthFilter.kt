package com.fork.forkfork.config.filter

import com.fork.forkfork.auth.util.JwtUtil
import io.github.bucket4j.Bucket
import io.github.bucket4j.local.LocalBucket
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Duration

class JwtAuthFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {
    private fun createNewBucket(): LocalBucket =
        Bucket.builder()
            .addLimit { limit -> limit.capacity(USER_BUCKET_SIZE).refillGreedy(REFILL_TOKENS, Duration.ofSeconds(1)) }
            .build()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (hasNotBearerToken(request)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = getBearerToken(request.getHeader(AUTHORIZATION))
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val userId = jwtUtil.getClaims(token).subject
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())

        val bucket = getBucket(request, userId)
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response)
            return
        }

        response.contentType = MediaType.TEXT_PLAIN_VALUE
        response.status = HttpStatus.TOO_MANY_REQUESTS.value()
        response.writer.write(TOO_MANY_REQUESTS)
    }

    private fun getBearerToken(authorization: String): String = authorization.removePrefix(BEARER_TOKEN_PREFIX)

    private fun hasNotBearerToken(request: HttpServletRequest): Boolean {
        val authorization: String? = request.getHeader(AUTHORIZATION)
        return authorization == null || !authorization.startsWith(BEARER_TOKEN_PREFIX)
    }

    private fun getBucket(
        httpServletRequest: HttpServletRequest,
        userId: String,
    ): Bucket {
        val session = httpServletRequest.getSession(true)
        val attributeName = BUCKET_ATTRIBUTE_NAME + userId
        return session.getAttribute(attributeName) as? Bucket ?: createAndSetBucket(session, attributeName)
    }

    private fun createAndSetBucket(
        session: HttpSession,
        attributeName: String,
    ): Bucket {
        val bucket = createNewBucket()
        session.setAttribute(attributeName, bucket)
        return bucket
    }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer "
        private const val USER_BUCKET_SIZE = 30L
        private const val REFILL_TOKENS = 5L
        private const val BUCKET_ATTRIBUTE_NAME = "bucket"
        private const val TOO_MANY_REQUESTS = "Too many requests"
    }
}
