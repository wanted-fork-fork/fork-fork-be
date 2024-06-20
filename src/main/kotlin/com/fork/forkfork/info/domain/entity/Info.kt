package com.fork.forkfork.info.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("info")
class Info(
    @Id val id: String,
    val matchMakerId: String,
    val authorId: String?,
    val userInfo: UserInfo,
    val idealPartner: IdealPartner,
) : BaseAuditor()
