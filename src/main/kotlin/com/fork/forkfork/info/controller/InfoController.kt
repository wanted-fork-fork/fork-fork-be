package com.fork.forkfork.info.controller

import com.fork.forkfork.info.domain.enums.Town
import com.fork.forkfork.info.dto.CityDto
import com.fork.forkfork.info.dto.TownDto
import com.fork.forkfork.info.dto.request.SaveInfoRequest
import com.fork.forkfork.info.dto.response.ArchivedInfoResponse
import com.fork.forkfork.info.dto.response.CityAndTownResponse
import com.fork.forkfork.info.dto.response.DetailedInfoResponse
import com.fork.forkfork.info.service.InfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/info")
class InfoController(val infoService: InfoService) {
    @GetMapping("/address")
    fun getAddress(): ResponseEntity<List<CityAndTownResponse>> =
        ResponseEntity.ok().body(
            Town.entries.groupBy { it.city }.entries.map { entry ->
                CityAndTownResponse(
                    CityDto(entry.key, entry.key.cityName),
                    entry.value.map { TownDto(it, it.townName) },
                )
            },
        )

    @PostMapping("/save/{linkKey}")
    fun saveInfo(
        @PathVariable linkKey: String,
        @RequestBody saveInfoRequest: SaveInfoRequest,
    ): ResponseEntity<String> =
        ResponseEntity.ok().body(
            infoService.saveInfo(linkKey, saveInfoRequest.userInfo, saveInfoRequest.idealPartner),
        )

    @GetMapping("/detail/{id}")
    fun getInfo(
        @PathVariable id: String,
    ): ResponseEntity<DetailedInfoResponse> = ResponseEntity.ok().body(infoService.getDetailedInfoById(id))

    @GetMapping("/all")
    fun getAllInfo(): ResponseEntity<List<ArchivedInfoResponse>> = ResponseEntity.ok().body(infoService.getAllInfo())

    @DeleteMapping("/delete/{id}")
    fun deleteInfo(
        @PathVariable id: String,
    ): ResponseEntity<String> {
        infoService.deleteInfo(id)
        return ResponseEntity.ok().body("Info deleted")
    }
}
