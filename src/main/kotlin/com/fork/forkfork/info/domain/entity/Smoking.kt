package com.fork.forkfork.info.domain.entity

class Smoking(
    val smokingCategory: SmokingCategory,
    val smokingAmount: String?,
)

enum class SmokingCategory {
    NON_SMOKER,
    SMOKER,
    ETC,
}
