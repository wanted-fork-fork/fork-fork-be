package com.fork.forkfork.auth.dto

import com.fork.forkfork.auth.domain.enums.OAuthCompany

class LoginInfoDto(
    val name: String,
    val profileImage: String?,
    val oauthId: Long,
    val oauthCompany: OAuthCompany,
)
