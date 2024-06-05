package com.fork.forkfork.image.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.fork.forkfork.image.exception.ImageUploadException
import com.fork.forkfork.image.properties.S3KeyProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ImageService(val amazonS3Client: AmazonS3Client, val s3KeyProperties: S3KeyProperties) {
    private val logger = KotlinLogging.logger {}

    fun uploadImage(image: MultipartFile): String {
        logger.error { "Uploading image / start" }
        val originName = image.originalFilename
        requireNotNull(originName) { FILE_NOT_FOUND_ERROR_MESSAGE }

        val newImageName = getNewImageName(originName)
        val metaData = createMetaData(image)

        try {
            logger.error { "Uploading image / putImageToS3" }
            putImageToS3(image, newImageName, metaData)
        } catch (e: Exception) {
            throw ImageUploadException(FAILED_TO_UPLOAD_IMAGE_ERROR_MESSAGE, e)
        }
        logger.error { "Uploading image / finish" }
        return getS3ImageUrl(newImageName)
    }

    private fun getNewImageName(originName: String): String {
        return UUID.randomUUID().toString() + CONNECTION_DELIMITER + originName
    }

    private fun createMetaData(image: MultipartFile): ObjectMetadata {
        val metaData = ObjectMetadata()
        metaData.contentType = image.contentType
        return metaData
    }

    private fun putImageToS3(
        image: MultipartFile,
        newImageName: String,
        metaData: ObjectMetadata,
    ) {
        val putObjectRequest =
            PutObjectRequest(
                s3KeyProperties.bucket,
                newImageName,
                image.inputStream,
                metaData,
            ).withCannedAcl(CannedAccessControlList.PublicRead)
        amazonS3Client.putObject(putObjectRequest)
    }

    private fun getS3ImageUrl(newImageName: String): String {
        return amazonS3Client.getUrl(s3KeyProperties.bucket, newImageName).toString()
    }

    companion object {
        const val FILE_NOT_FOUND_ERROR_MESSAGE = "File not found"
        const val FAILED_TO_UPLOAD_IMAGE_ERROR_MESSAGE = "Failed to upload image"
        const val CONNECTION_DELIMITER = "_"
    }
}
