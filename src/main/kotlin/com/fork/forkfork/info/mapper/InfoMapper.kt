package com.fork.forkfork.info.mapper

import com.fork.forkfork.info.domain.entity.IdealPartner
import com.fork.forkfork.info.domain.entity.UserInfo
import com.fork.forkfork.info.dto.DetailedInfoIdealPartner
import com.fork.forkfork.info.dto.DetailedInfoUserInfo
import com.fork.forkfork.info.dto.request.IdealPartnerRequest
import com.fork.forkfork.info.dto.request.UserInfoRequest
import com.fork.forkfork.info.dto.response.ArchivedInfoResponse
import com.fork.forkfork.info.dto.response.InfoToShareIdealPartner
import com.fork.forkfork.info.dto.response.InfoToShareUserInfo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface InfoMapper {
    fun toUserInfoFromUserInfoRequest(userInfoRequest: UserInfoRequest): UserInfo

    fun toIdealPartnerFromIdealPartnerRequest(idealPartnerRequest: IdealPartnerRequest?): IdealPartner?

    @Mapping(target = "id", ignore = true)
    fun toArchivedInfoResponseFromUserInfo(userInfo: UserInfo): ArchivedInfoResponse

    fun toDetailedInfoUserInfoFromUserInfo(userInfo: UserInfo): DetailedInfoUserInfo

    fun toDetailedInfoIdealPartnerFromIdealPartner(idealPartner: IdealPartner?): DetailedInfoIdealPartner?

    @Mapping(target = "introduction", source = "introduction")
    fun toUserInfoFromDetailedInfoUserInfoAndIntroduction(
        userInfo: DetailedInfoUserInfo,
        introduction: String?,
    ): UserInfo

    fun toIdealPartnerFromDetailedInfoIdealPartner(idealPartner: DetailedInfoIdealPartner?): IdealPartner?

    fun toInfoToShareUserInfoFromUserInfo(userInfo: UserInfo): InfoToShareUserInfo

    fun toInfoToShareIdealPartnerFromIdealPartner(idealPartner: IdealPartner?): InfoToShareIdealPartner?
}
