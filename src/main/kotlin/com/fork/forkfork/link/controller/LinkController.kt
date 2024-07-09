package com.fork.forkfork.link.controller

import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.link.dto.request.UpdateLinkOpenRequest
import com.fork.forkfork.link.dto.response.ValidateLinkResponse
import com.fork.forkfork.link.service.LinkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/link")
class LinkController(val linkService: LinkService) {
    @PostMapping
    fun createLink() = ResponseEntity.ok().body(linkService.createLink())

    @GetMapping("/status")
    fun getLinkByMatchMakerId() = ResponseEntity.ok().body(linkService.getLinkKeyByMatchMakerId(getUserIdFromSecurityContext()))

    @PutMapping("/key")
    fun regenerateLinkKey() = ResponseEntity.ok().body(linkService.regenerateLinkKeyByMatchMakerId(getUserIdFromSecurityContext()))

    @GetMapping("/valid/{linkKey}")
    fun validateLink(
        @PathVariable linkKey: String,
    ) = ResponseEntity.ok().body(ValidateLinkResponse(linkService.isValidLink(linkKey), linkKey))

    @PutMapping("/link-open")
    fun updateLinkOpen(
        @RequestBody updateLinkOpenRequest: UpdateLinkOpenRequest,
    ) = ResponseEntity.ok().body(linkService.updateLinkOpen(updateLinkOpenRequest.linkId, updateLinkOpenRequest.isOpen))
}
