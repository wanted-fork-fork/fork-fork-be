package com.fork.forkfork.info.service

import com.fork.forkfork.auth.util.AuthUtil.getUserIdFromSecurityContext
import com.fork.forkfork.info.domain.entity.Info
import com.fork.forkfork.info.domain.repository.InfoRepository
import com.fork.forkfork.info.dto.DetailedInfoDto
import com.fork.forkfork.info.dto.DetailedInfoIdealPartner
import com.fork.forkfork.info.dto.DetailedInfoUserInfo
import com.fork.forkfork.info.dto.request.IdealPartnerRequest
import com.fork.forkfork.info.dto.request.UserInfoRequest
import com.fork.forkfork.info.dto.response.ArchivedInfoResponse
import com.fork.forkfork.info.mapper.InfoMapper
import com.fork.forkfork.link.service.LinkService
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository, val infoMapper: InfoMapper, val linkService: LinkService) {
    fun getDetailedInfoById(id: String): DetailedInfoDto {
        val info = getInfoWithValidation(id)
        require(info.matchMakerId == getUserIdFromSecurityContext()) { "You are not authorized to view this info" }

        return DetailedInfoDto(
            id = info.id ?: throw Exception("Info id is null"),
            userInfo = infoMapper.toUserInfoRequestFromUserInfo(info.userInfo),
            idealPartner = infoMapper.toIdealPartnerRequestFromIdealPartner(info.idealPartner),
        )
    }

    fun saveInfo(
        linkKey: String,
        userInfo: UserInfoRequest,
        idealPartner: IdealPartnerRequest,
    ) = Info(
        matchMakerId = linkService.getMatchMakerIdByLinkKey(linkKey),
        authorId = getUserIdFromSecurityContext(),
        userInfo = infoMapper.toUserInfoFromUserInfoRequest(userInfo),
        idealPartner = infoMapper.toIdealPartnerFromIdealPartnerRequest(idealPartner),
    ).let { infoRepository.save(it) }.id

    fun getAllInfo(): List<ArchivedInfoResponse> {
        val userId = getUserIdFromSecurityContext()
        return infoRepository.findAllByMatchMakerId(userId).map { info ->
            infoMapper.toArchivedInfoResponseFromUserInfo(info.userInfo).apply { id = info.id }
        }
    }

    fun deleteInfo(id: String) {
        val info = getInfoWithValidation(id)
        require(info.matchMakerId == getUserIdFromSecurityContext()) { "You are not authorized to delete this info" }
        infoRepository.deleteById(id)
    }

    fun updateInfo(
        id: String,
        detailedInfoUserInfo: DetailedInfoUserInfo,
        detailedInfoIdealPartner: DetailedInfoIdealPartner,
    ) {
        val info = getInfoWithValidation(id)
        require(info.matchMakerId == getUserIdFromSecurityContext()) { "You are not authorized to update this info" }
        val userInfo = infoMapper.toUserInfoFromDetailedInfoUserInfoAndIntroduction(detailedInfoUserInfo, info.userInfo.introduction)
        val idealPartner = infoMapper.toIdealPartnerFromDetailedInfoIdealPartner(detailedInfoIdealPartner)
        info.userInfo = userInfo
        info.idealPartner = idealPartner
        infoRepository.save(info)
    }

    private fun getInfoWithValidation(id: String): Info {
        val info = infoRepository.findById(id).orElseThrow { throw IllegalArgumentException("Info not found") }
        return info
    }
}
