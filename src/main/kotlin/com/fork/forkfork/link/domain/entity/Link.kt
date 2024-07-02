package com.fork.forkfork.link.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("links")
class Link(
    @Id
    var id: String? = null,
) : BaseAuditor()
