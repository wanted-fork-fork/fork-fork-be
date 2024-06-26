package com.fork.forkfork.info.mapper

import com.fork.forkfork.info.domain.entity.IdealPartner
import com.fork.forkfork.info.domain.entity.UserInfo
import com.fork.forkfork.info.dto.request.IdealPartnerRequest
import com.fork.forkfork.info.dto.request.UserInfoRequest
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface InfoMapper {
    fun toUserInfoFromUserInfoRequest(userInfoRequest: UserInfoRequest): UserInfo

    fun toIdealPartnerFromIdealPartnerRequest(idealPartnerRequest: IdealPartnerRequest): IdealPartner
}
