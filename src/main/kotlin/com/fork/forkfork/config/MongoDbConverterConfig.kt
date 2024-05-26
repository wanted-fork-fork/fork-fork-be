package com.fork.forkfork.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.OffsetDateTime
import java.util.Date

@Configuration
class MongoDbConverterConfig {
    @Bean
    @Primary
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(listOf(OffsetDateTimeReadConverter(), OffsetDateTimeWriteConverter()))
    }

    @WritingConverter
    class OffsetDateTimeWriteConverter : Converter<OffsetDateTime, Date> {
        override fun convert(source: OffsetDateTime): Date {
            return Date.from(source.toInstant())
        }
    }

    @ReadingConverter
    class OffsetDateTimeReadConverter : Converter<Date, OffsetDateTime> {
        override fun convert(source: Date): OffsetDateTime {
            return source.toInstant().atOffset(OffsetDateTime.now().offset)
        }
    }
}
