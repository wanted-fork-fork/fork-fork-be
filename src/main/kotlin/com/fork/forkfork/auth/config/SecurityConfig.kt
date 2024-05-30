package com.fork.forkfork.auth.config

import com.fork.forkfork.auth.filter.JwtAuthFilter
import com.fork.forkfork.auth.util.JwtUtil
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtUtil: JwtUtil) {
    @Bean
    fun api(): OpenAPI {
        val api: SecurityScheme =
            SecurityScheme().type(
                SecurityScheme.Type.HTTP,
            ).`in`(SecurityScheme.In.HEADER).scheme(BEARER).bearerFormat(JWT)
        val securityRequirement = SecurityRequirement().addList(BEARER_TOKEN)

        return OpenAPI().components(Components().addSecuritySchemes(BEARER_TOKEN, api)).addSecurityItem(securityRequirement)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.formLogin { it.disable() }
        http.httpBasic { it.disable() }
        http.addFilterBefore(JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
        http.authorizeHttpRequests { it.requestMatchers(*WHITE_LIST).permitAll().anyRequest().authenticated() }
        return http.build()
    }

    companion object {
        private val WHITE_LIST = arrayOf("/api/v1/auth/kakao/login", "/", "/swagger-ui/**", "/v3/api-docs/**")
        private const val BEARER_TOKEN = "Bearer Token"
        private const val BEARER = "Bearer"
        private const val JWT = "JWT"
    }
}
