package com.fork.forkfork.info.domain.entity

import com.fork.forkfork.info.domain.enums.City
import com.fork.forkfork.info.domain.enums.Town

class Location(
    val cities: List<City>,
    val towns: List<Town>,
)
