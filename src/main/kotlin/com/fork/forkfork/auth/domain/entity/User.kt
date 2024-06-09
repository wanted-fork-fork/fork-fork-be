package com.fork.forkfork.auth.domain.entity

import com.fork.forkfork.auth.domain.enums.OAuthCompany
import com.fork.forkfork.domain.BaseAuditor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
@CompoundIndex(name = "oauthId_oauthCompany", def = "{'oauthId': 1, 'oauthCompany': 1}", unique = true)
class User(
    val name: String,
    val profileImage: String?,
    val oauthId: Long,
    val oauthCompany: OAuthCompany,
    @Id
    var id: String? = null,
) : BaseAuditor()
