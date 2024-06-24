package com.fork.forkfork.info.dto.request

import com.fork.forkfork.info.domain.entity.Drinking
import com.fork.forkfork.info.domain.entity.Image
import com.fork.forkfork.info.domain.entity.Location
import com.fork.forkfork.info.domain.entity.Religion
import com.fork.forkfork.info.domain.entity.Smoking

class IdealPartnerRequest(
    val ageRange: IntRange?,
    val heightRange: IntRange?,
    val style: String?,
    val images: List<Image>?,
    val location: Location?,
    val hobbies: List<String>?,
    val religion: Religion?,
    val drinking: Drinking?,
    val smoking: Smoking?,
    val requiredOptions: List<String>?,
    val toMatchMaker: String?,
)
