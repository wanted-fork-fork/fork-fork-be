package com.fork.forkfork

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {
    private val log = KotlinLogging.logger { }

    @GetMapping
    fun health(): String {
        return "OK"
    }

    @GetMapping("/log")
    fun log(): String {
        log.error { "Error log" }
        log.warn { "Warn log" }
        log.info { "Info log" }
        return "OK"
    }
}
