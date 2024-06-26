package com.fork.forkfork.info.domain.entity

class IdealPartner(
    val ageRange: NumberRange?,
    val heightRange: NumberRange?,
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
