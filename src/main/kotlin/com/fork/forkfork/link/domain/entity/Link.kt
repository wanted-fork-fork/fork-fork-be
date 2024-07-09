package com.fork.forkfork.link.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("links")
class Link(
    @Indexed(unique = true)
    var key: String,
    @Indexed(unique = true)
    val matchMakerId: String,
    var isOpen: Boolean,
    @Id
    var id: String? = null,
) : BaseAuditor()
