package com.fork.forkfork.image.controller

import com.fork.forkfork.image.dto.ImageDto
import com.fork.forkfork.image.service.ImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/v1/image")
class ImageController(val imageService: ImageService) {
    @PostMapping("/upload", consumes = ["multipart/form-data"])
    fun uploadImage(
        @RequestParam image: MultipartFile,
    ): ResponseEntity<ImageDto> = ResponseEntity.ok(imageService.uploadImage(image))
}
