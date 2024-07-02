package com.fork.forkfork.info.service

import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.info.domain.entity.Info
import com.fork.forkfork.info.domain.repository.InfoRepository
import com.fork.forkfork.info.dto.request.IdealPartnerRequest
import com.fork.forkfork.info.dto.request.UserInfoRequest
import com.fork.forkfork.info.mapper.InfoMapper
import com.fork.forkfork.link.service.LinkService
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository, val infoMapper: InfoMapper, val linkService: LinkService) {
    fun getInfoById(id: String) = infoRepository.findById(id).get()

    fun saveInfo(
        linkId: String,
        userInfo: UserInfoRequest,
        idealPartner: IdealPartnerRequest,
    ) = Info(
        matchMakerId = linkService.getMatchMakerIdByLinkId(linkId),
        authorId = getUserIdFromSecurityContext(),
        userInfo = infoMapper.toUserInfoFromUserInfoRequest(userInfo),
        idealPartner = infoMapper.toIdealPartnerFromIdealPartnerRequest(idealPartner),
    ).let { infoRepository.save(it) }.id
}
