package com.fork.forkfork.info.domain.entity

class Drinking(
    val drinkingCategory: DrinkingCategory,
    val drinkingAmount: String,
)

enum class DrinkingCategory {
    NO_PROBLEM,
    ONE_TWO_TIMES_A_WEEK,
    ONE_TWO_TIMES_A_MONTH,
    NEVER,
    ETC,
}
