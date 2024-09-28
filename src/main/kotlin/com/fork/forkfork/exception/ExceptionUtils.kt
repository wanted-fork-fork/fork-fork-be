package com.fork.forkfork.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object ExceptionUtils {
    fun notFoundException(message: String): ResponseStatusException = throw ResponseStatusException(HttpStatus.NOT_FOUND, message)
}
