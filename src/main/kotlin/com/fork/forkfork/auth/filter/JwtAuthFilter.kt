package com.fork.forkfork.auth.filter

import com.fork.forkfork.auth.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {
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
        jwtUtil.validateToken(token)
        val userId = jwtUtil.getClaims(token).subject
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
        filterChain.doFilter(request, response)
    }

    private fun getBearerToken(authorization: String): String = authorization.removePrefix(BEARER_TOKEN_PREFIX)

    private fun hasNotBearerToken(request: HttpServletRequest): Boolean {
        val authorization: String? = request.getHeader(AUTHORIZATION)
        return authorization == null || !authorization.startsWith(BEARER_TOKEN_PREFIX)
    }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer "
    }
}
