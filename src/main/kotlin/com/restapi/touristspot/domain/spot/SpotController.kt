package com.restapi.touristspot.domain.spot

import Spot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("spots")
class SpotController {

    @Autowired
    lateinit var spotService: SpotService

    @GetMapping
    fun spotInFiveKMFrom(@RequestParam longitude: Double, @RequestParam latitude: Double) = spotService
            .findSpotsInFiveKm(longitude, latitude)

    @GetMapping("/name")
    fun spotByName(@RequestParam name: String) = spotService.findByName(name)

    @PostMapping
    fun saveSpot(@RequestBody spot: Spot) = spotService.save(spot)

}