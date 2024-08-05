package com.fork.forkfork.sharing.controller

import com.fork.forkfork.sharing.dto.response.SaveSharingResponse
import com.fork.forkfork.sharing.service.SharingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/sharing")
class SharingController(val sharingService: SharingService) {
    @PostMapping("/{infoId}")
    fun saveSharing(
        @PathVariable infoId: String,
    ) = ResponseEntity.ok().body(SaveSharingResponse(sharingService.saveSharing(infoId)))

    @GetMapping("/{sharingId}")
    fun getInfoBySharingId(
        @PathVariable sharingId: String,
    ) = ResponseEntity.ok().body(sharingService.getInfoBySharing(sharingId))
}
