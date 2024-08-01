package com.fork.forkfork.info.dto

import com.fork.forkfork.info.domain.entity.Book
import com.fork.forkfork.info.domain.entity.Drinking
import com.fork.forkfork.info.domain.entity.Gender
import com.fork.forkfork.info.domain.entity.InfoImage
import com.fork.forkfork.info.domain.entity.Job
import com.fork.forkfork.info.domain.entity.Location
import com.fork.forkfork.info.domain.entity.MBTI
import com.fork.forkfork.info.domain.entity.Movie
import com.fork.forkfork.info.domain.entity.NumberRange
import com.fork.forkfork.info.domain.entity.Religion
import com.fork.forkfork.info.domain.entity.Smoking
import java.util.Date

class DetailedInfoDto(
    val id: String,
    val userInfo: DetailedInfoUserInfo,
    val idealPartner: DetailedInfoIdealPartner,
)

class DetailedInfoUserInfo(
    val name: String,
    val gender: Gender,
    val birthDate: Date,
    val height: Int,
    val images: List<InfoImage>,
    val mbti: MBTI,
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
)

class DetailedInfoIdealPartner(
    val ageRange: NumberRange?,
    val heightRange: NumberRange?,
    val style: String?,
    val images: List<InfoImage>?,
    val location: Location?,
    val hobbies: List<String>?,
    val religion: Religion?,
    val drinking: Drinking?,
    val smoking: Smoking?,
    val requiredOptions: List<String>?,
    val toMatchMaker: String?,
)
