package com.fork.forkfork.link.service

import com.fork.forkfork.auth.service.AuthService
import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.link.domain.entity.Link
import com.fork.forkfork.link.domain.repository.LinkRepository
import com.fork.forkfork.link.dto.response.CreateLinkResponse
import com.fork.forkfork.link.dto.response.LinkStatusResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class LinkService(
    private val linkRepository: LinkRepository,
    private val authService: AuthService,
) {
    fun getMatchMakerIdByLinkKey(linkKey: String): String {
        val link = findLinkByKey(linkKey)
        return link.matchMakerId
    }

    fun getLinkKeyByMatchMakerId(matchMakerId: String): LinkStatusResponse {
        val link =
            linkRepository.findByMatchMakerId(matchMakerId).orElseThrow {
                notFoundException("Link not found, matchMakerId : $matchMakerId")
            }
        return LinkStatusResponse(link.key, link.isOpen)
    }

    fun isValidLink(linkKey: String): Boolean {
        val link = findLinkByKey(linkKey)
        return authService.isExistUser(link.matchMakerId) && link.isOpen
    }

    fun createLink(): CreateLinkResponse {
        val link = linkRepository.save(Link(getNewKey(), getUserIdFromSecurityContext(), true))
        return CreateLinkResponse(link.id ?: throw RuntimeException("Link not created"))
    }

    private fun findLinkByKey(linkKey: String): Link =
        linkRepository.findByKey(linkKey).orElseThrow {
            notFoundException("Link not found, linkKey : $linkKey")
        }

    private fun getNewKey(): String {
        var key: String
        do {
            key = UUID.randomUUID().toString().replace("-", "")
        } while (linkRepository.existsByKey(key))
        return key
    }

    private fun notFoundException(message: String): ResponseStatusException = throw ResponseStatusException(HttpStatus.NOT_FOUND, message)
}
