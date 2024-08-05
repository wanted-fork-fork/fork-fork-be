package com.fork.forkfork.sharing.domain.entity

import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.OffsetDateTime

@Document("sharing")
class Sharing(
    val infoId: String,
    val expiredDate: OffsetDateTime,
    @Id
    var id: String? = null,
) : BaseAuditor()
