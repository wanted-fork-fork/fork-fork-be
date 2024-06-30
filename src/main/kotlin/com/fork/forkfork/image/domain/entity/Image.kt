package com.fork.forkfork.image.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("images")
class Image(
    @Id
    val id: String,
    val imageName: String,
    val size: Long,
) : BaseAuditor()
