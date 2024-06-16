package com.fork.forkfork.info.controller

import com.fork.forkfork.info.domain.enums.Town
import com.fork.forkfork.info.dto.CityDto
import com.fork.forkfork.info.dto.TownDto
import com.fork.forkfork.info.dto.response.CityAndTownResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/info")
class InfoController {
    @GetMapping("/address")
    fun getAddress(): ResponseEntity<List<CityAndTownResponse>> =
        ResponseEntity.ok()
            .body(
                Town.entries.groupBy { it.city }.entries.map { entry ->
                    CityAndTownResponse(
                        CityDto(entry.key, entry.key.cityName),
                        entry.value.map { TownDto(it, it.townName) },
                    )
                },
            )
}
