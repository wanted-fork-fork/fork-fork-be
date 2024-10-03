package com.fork.forkfork.info.dto.response

import com.fork.forkfork.info.domain.entity.Book
import com.fork.forkfork.info.domain.entity.Gender
import com.fork.forkfork.info.domain.entity.InfoImage
import com.fork.forkfork.info.domain.entity.Job
import com.fork.forkfork.info.domain.entity.Location
import com.fork.forkfork.info.domain.entity.MBTI
import com.fork.forkfork.info.domain.entity.Movie
import com.fork.forkfork.info.domain.entity.Religion
import com.fork.forkfork.info.domain.entity.Smoking
import java.time.OffsetDateTime
import java.util.Date

class InfoToShareResponse(
    val sharingId: String,
    val userInfo: InfoToShareUserInfo,
    val expiredDate: OffsetDateTime,
)

class InfoToShareUserInfo(
    val gender: Gender,
    val birthDate: Date,
    val height: Int,
    val images: List<InfoImage>,
    val mbti: MBTI?,
    val job: Job,
    val location: Location,
    val religion: Religion,
    val hobbies: List<String>,
    val drinking: String,
    val smoking: Smoking,
    val pets: List<String>?,
    val foods: List<String>?,
    val dateStyle: List<String>?,
    val book: Book?,
    val movie: Movie?,
    val introduction: String,
)
