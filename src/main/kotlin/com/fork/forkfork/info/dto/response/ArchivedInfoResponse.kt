package com.fork.forkfork.info.dto.response

import com.fork.forkfork.info.domain.entity.Gender
import com.fork.forkfork.info.domain.entity.Job
import com.fork.forkfork.info.domain.entity.Location
import com.fork.forkfork.info.domain.entity.MBTI
import com.fork.forkfork.info.domain.entity.Religion
import com.fork.forkfork.info.domain.entity.Smoking
import java.util.Date

class ArchivedInfoResponse(
    var id: String?,
    val name: String,
    val birthDate: Date,
    val gender: Gender,
    val location: Location,
    val height: Int,
    val mbti: MBTI?,
    val religion: Religion,
    val job: Job,
    val drinking: String,
    val smoking: Smoking,
    val hobbies: List<String>,
)
