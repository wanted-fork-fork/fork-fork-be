package com.fork.forkfork.link.service

import com.fork.forkfork.auth.service.AuthService
import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.link.domain.entity.Link
import com.fork.forkfork.link.domain.repository.LinkRepository
import com.fork.forkfork.link.dto.response.CreateLinkResponse
import com.fork.forkfork.link.properties.LinkProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.OffsetDateTime
import java.util.UUID

@Service
class LinkService(
    private val linkRepository: LinkRepository,
    private val authService: AuthService,
    private val linkProperties: LinkProperties,
) {
    fun getMatchMakerIdByLinkId(linkId: String): String {
        val link = findLinkById(linkId)
        validateLinkExpiration(link)
        return getMatchMakerId(link)
    }

    fun getLinkKeyByMatchMakerId(matchMakerId: String): String =
        linkRepository.findByMatchMakerId(matchMakerId).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found, matchMakerId : $matchMakerId")
        }.key

    fun isValidLink(linkId: String): Boolean {
        val link = findLinkById(linkId)
        validateLinkExpiration(link)
        return authService.isExistUser(link.createdBy ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "CreatedBy not found in Link"))
    }

    fun createLink(): CreateLinkResponse {
        val link = linkRepository.save(Link(getNewKey(), getUserIdFromSecurityContext(), true))
        return CreateLinkResponse(link.id ?: throw RuntimeException("Link not created"))
    }

    private fun getNewKey(): String {
        val key = UUID.randomUUID().toString().replace("-", "")
        return if (linkRepository.existsByKey(key)) {
            getNewKey()
        } else {
            key
        }
    }

    private fun findLinkById(linkId: String): Link =
        linkRepository.findById(linkId).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found") }

    private fun validateLinkExpiration(link: Link) {
        if (OffsetDateTime.now().minusHours(linkProperties.expirationHours).isAfter(link.createdDate)) {
            throw ResponseStatusException(HttpStatus.GONE, "Link expired")
        }
    }

    private fun getMatchMakerId(link: Link): String {
        val matchMakerId = link.createdBy ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "CreatedBy not found in Link")
        if (!authService.isExistUser(matchMakerId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "MatchMakerId not user")
        }
        return matchMakerId
    }
}
