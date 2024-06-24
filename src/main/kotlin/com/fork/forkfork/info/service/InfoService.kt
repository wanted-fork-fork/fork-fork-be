package com.fork.forkfork.info.service

import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.info.domain.entity.Info
import com.fork.forkfork.info.domain.repository.InfoRepository
import com.fork.forkfork.info.dto.request.IdealPartnerRequest
import com.fork.forkfork.info.dto.request.UserInfoRequest
import com.fork.forkfork.info.mapper.InfoMapper
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository, val infoMapper: InfoMapper) {
    fun getInfoById(id: String) = infoRepository.findById(id)

    fun saveInfo(
        info: String,
        userInfo: UserInfoRequest,
        idealPartner: IdealPartnerRequest,
    ) = Info(
        matchMakerId = info,
        authorId = getUserIdFromSecurityContext(),
        userInfo = infoMapper.toUserInfoFromUserInfoRequest(userInfo),
        idealPartner = infoMapper.toIdealPartnerFromIdealPartnerRequest(idealPartner),
    ).let { infoRepository.save(it) }.id
}
