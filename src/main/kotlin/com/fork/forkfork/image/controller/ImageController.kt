package com.fork.forkfork.image.controller

import com.fork.forkfork.image.dto.ImageDto
import com.fork.forkfork.image.service.ImageService
import io.github.bucket4j.Bucket
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.Duration.ofSeconds

@RestController
@RequestMapping("api/v1/image")
class ImageController(val imageService: ImageService) {
    private val bucket = Bucket.builder().addLimit { it.capacity(30).refillGreedy(30, ofSeconds(30)) }.build()

    @PostMapping("/upload", consumes = ["multipart/form-data"])
    fun uploadImage(
        @RequestParam image: MultipartFile,
    ): ResponseEntity<ImageDto> {
        val existedImage = imageService.isExistedImage(image)
        if (existedImage != null) {
            return ResponseEntity.ok(existedImage)
        }
        bucket.asBlocking().consume(1)
        val imageDto = imageService.uploadImage(image)
        return ResponseEntity.ok(imageDto)
    }
}
