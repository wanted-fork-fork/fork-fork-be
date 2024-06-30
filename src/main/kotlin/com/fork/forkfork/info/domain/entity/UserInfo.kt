package com.fork.forkfork.info.domain.entity

import java.util.Date

class UserInfo(
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
    val introduction: String?,
    val pets: List<String>?,
    val foods: List<String>?,
    val dateStyle: List<String>?,
    val book: Book?,
    val movie: Movie?,
)
