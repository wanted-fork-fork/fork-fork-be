package com.fork.forkfork.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TestController {
    @GetMapping("/test")
    fun returnTest(): String {
        return "TEST"
    }
}
