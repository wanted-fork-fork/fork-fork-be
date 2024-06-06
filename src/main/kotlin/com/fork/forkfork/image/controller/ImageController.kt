package com.fork.forkfork.image.controller

import com.fork.forkfork.image.service.ImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    ): ResponseEntity<String> = ResponseEntity.ok(imageService.uploadImage(image))

    @PostMapping("/test", consumes = ["multipart/form-data"])
    fun test(
        @RequestParam image: MultipartFile,
    ): ResponseEntity<String> = ResponseEntity.ok("test")

    @PostMapping("/test2")
    fun test2(): ResponseEntity<String> = ResponseEntity.ok("test2")

    @GetMapping("/test3")
    fun test3(): ResponseEntity<String> = ResponseEntity.ok("test3")
}
