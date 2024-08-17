package com.fork.forkfork.config

import com.fork.forkfork.auth.config.CustomAuthenticationEntryPoint
import com.fork.forkfork.auth.util.JwtUtil
import com.fork.forkfork.config.filter.JwtAuthFilter
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtUtil: JwtUtil, val authenticationEntryPoint: CustomAuthenticationEntryPoint) {
    @Bean
    fun api(): OpenAPI {
        val api: SecurityScheme =
            SecurityScheme().type(
                SecurityScheme.Type.HTTP,
            ).`in`(SecurityScheme.In.HEADER).scheme(BEARER).bearerFormat(JWT)
        val securityRequirement = SecurityRequirement().addList(BEARER_TOKEN)

        return OpenAPI().components(Components().addSecuritySchemes(BEARER_TOKEN, api)).addSecurityItem(securityRequirement).info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("GOOGOO API")
            .description("GOOGOO API Documentation")
            .version("0.0.1")
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        http.cors { it.configurationSource(corsConfigurationSource()) }
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.formLogin { it.disable() }
        http.httpBasic { it.disable() }
        http.addFilterBefore(JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
        http.exceptionHandling { it.authenticationEntryPoint(authenticationEntryPoint) }
        http.authorizeHttpRequests { it.requestMatchers(*WHITE_LIST).permitAll().anyRequest().authenticated() }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "https://bhsocdpuaz.us14.qoddiapp.com", "https://www.meetgoogoo.com")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.maxAge = 3600
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    companion object {
        private val WHITE_LIST =
            arrayOf(
                "/api/v1/auth/kakao/login",
                "api/v1/auth/refresh-token",
                "/",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/api/v1/info/save/*",
                "/api/v1/image/upload",
                "/api/v1/link/valid/*",
                "/v3/api-docs.yaml",
                "/error",
            )
        private const val BEARER_TOKEN = "Bearer Token"
        private const val BEARER = "Bearer"
        private const val JWT = "JWT"
    }
}
