package com.fork.forkfork.sharing.service

import com.fork.forkfork.info.service.InfoService
import com.fork.forkfork.sharing.domain.entity.Sharing
import com.fork.forkfork.sharing.domain.repository.SharingRepository
import com.fork.forkfork.sharing.properties.SharingProperties
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class SharingService(val sharingRepository: SharingRepository, val sharingProperties: SharingProperties, val infoService: InfoService) {
    fun saveSharing(infoId: String): String {
        val sharing =
            Sharing(
                infoService.getDetailedInfoById(infoId).id,
                OffsetDateTime.now().plusHours(sharingProperties.expirationHours),
            ).let { sharingRepository.save(it) }
        return sharing.id ?: throw IllegalArgumentException("Not found sharing id after saving")
    }
}
