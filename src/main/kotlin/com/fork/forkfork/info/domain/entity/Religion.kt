package com.fork.forkfork.info.domain.entity

class Religion(
    val religionCategory: ReligionCategory,
    val religionName: String?,
)

enum class ReligionCategory {
    CHRISTIANITY,
    CATHOLICISM,
    BUDDHISM,
    IRRELIGION,
    ETC,
}
