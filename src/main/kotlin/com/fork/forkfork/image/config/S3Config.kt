package com.fork.forkfork.image.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.fork.forkfork.image.properties.S3KeyProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    val s3KeyProperties: S3KeyProperties,
) {
    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val credentials = BasicAWSCredentials(s3KeyProperties.accessKey, s3KeyProperties.secretKey)
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.AP_NORTHEAST_2)
            .build() as AmazonS3Client
    }
}
