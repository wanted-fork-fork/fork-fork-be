package com.fork.forkfork.exception.handler

import com.fork.forkfork.exception.InvalidTokenException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(): ResponseEntity<String> = ResponseEntity.badRequest().body("Invalid token")

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<String> = ResponseEntity.badRequest().body(e.message)
}
