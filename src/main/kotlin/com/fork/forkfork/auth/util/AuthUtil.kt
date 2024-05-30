package com.fork.forkfork.auth.util

import org.springframework.security.core.context.SecurityContextHolder

object AuthUtil {
    fun getUserIdFromSecurityContext(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }
}
