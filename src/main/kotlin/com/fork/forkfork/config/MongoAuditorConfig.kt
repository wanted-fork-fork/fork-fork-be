package com.fork.forkfork.config

import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import java.time.OffsetDateTime
import java.util.Optional

@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@Configuration
class MongoAuditorConfig {
    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware {
            Optional.of(getUserIdFromSecurityContext())
        }
    }

    @Bean("auditingDateTimeProvider")
    fun dateTimeProvider(): DateTimeProvider = DateTimeProvider { Optional.of(OffsetDateTime.now()) }
}

