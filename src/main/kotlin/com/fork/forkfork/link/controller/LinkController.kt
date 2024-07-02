package com.fork.forkfork.link.controller

import com.fork.forkfork.link.dto.response.ValidateLinkResponse
import com.fork.forkfork.link.service.LinkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/link")
class LinkController(val linkService: LinkService) {
    @PostMapping
    fun createLink() = ResponseEntity.ok().body(linkService.createLink())

    @GetMapping("/valid/{linkId}")
    fun validateLink(
        @PathVariable linkId: String,
    ) = ResponseEntity.ok().body(ValidateLinkResponse(linkService.isValidLink(linkId), linkId))
}
