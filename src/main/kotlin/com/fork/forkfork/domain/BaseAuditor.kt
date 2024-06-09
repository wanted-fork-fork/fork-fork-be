package com.fork.forkfork.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime

open class BaseAuditor(
    @CreatedBy var createdBy: String? = null,
    @CreatedDate var createdDate: OffsetDateTime? = null,
    @LastModifiedBy var updatedBy: String? = null,
    @LastModifiedDate var updatedDate: OffsetDateTime? = null,
)
