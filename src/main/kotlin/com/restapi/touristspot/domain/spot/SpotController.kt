package com.restapi.touristspot.domain.spot

import com.restapi.touristspot.domain.comment.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("spots")
class SpotController {

    @Autowired
    lateinit var spotService: SpotService

    @GetMapping("/near")
    fun spotInFiveKMFrom(@RequestParam longitude: Double, @RequestParam latitude: Double) = spotService
            .findSpotsInFiveKm(longitude, latitude)

    @GetMapping("/search")
    fun spotByName(@RequestParam name: String) = spotService.findByName(name)

    @GetMapping
    fun all() = spotService.findAll()

    @PostMapping
    fun upload(@RequestParam("picture") picture: MultipartFile,
               @RequestParam("name") name: String,
               @RequestParam("category") category: String,
               @RequestParam("longitude") longitude: Double,
               @RequestParam("latitude") latitude: Double
    ) = spotService.save(picture, name, category, longitude, latitude)

    @PostMapping("/{spotId}/comments")
    fun addCommentInSpot(@PathVariable spotId: String, @RequestBody comment: String) = spotService.addCommentInSpot(spotId, comment)

    @GetMapping("/{spotId}/comments")
    fun findComments(@PathVariable spotId: String): List<Comment> = spotService.findComments(spotId)
}