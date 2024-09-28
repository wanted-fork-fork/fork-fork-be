package com.fork.forkfork.link.service

import com.fork.forkfork.auth.service.AuthService
import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.exception.ExceptionUtils.notFoundException
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
        val link = findLinkByMatchMakerId(matchMakerId)
        return LinkStatusResponse(link.getId(), link.key, link.isOpen)
    }

    fun isValidLink(linkKey: String): Boolean {
        val link = findLinkByKey(linkKey)
        return authService.isExistUser(link.matchMakerId) && link.isOpen
    }

    fun createLink(): CreateLinkResponse {
        linkRepository.findByMatchMakerId(getUserIdFromSecurityContext()).ifPresent {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Link already exists")
        }
        val link = linkRepository.save(Link(getNewKey(), getUserIdFromSecurityContext(), true))
        return CreateLinkResponse(link.getId(), link.key, link.isOpen)
    }

    fun regenerateLinkKeyByMatchMakerId(matchMakerId: String): CreateLinkResponse {
        val link =
            findLinkByMatchMakerId(matchMakerId).apply {
                key = getNewKey()
            }
        val newLink = linkRepository.save(link)
        return CreateLinkResponse(newLink.getId(), newLink.key, newLink.isOpen)
    }

    fun updateLinkOpen(
        linkId: String,
        open: Boolean,
    ) {
        val link =
            linkRepository.findById(linkId).orElseThrow {
                notFoundException("Link not found, linkId : $linkId")
            }
        link.isOpen = open
        linkRepository.save(link)
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

    private fun findLinkByMatchMakerId(matchMakerId: String): Link =
        linkRepository.findByMatchMakerId(matchMakerId).orElseThrow {
            notFoundException("Link not found, matchMakerId : $matchMakerId")
        }
}
