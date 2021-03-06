package com.restapi.touristspot.domain.spot

import com.restapi.touristspot.domain.comment.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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

    @GetMapping("/createdBy")
    fun spotsCreatedBy() = spotService.findByLoggedUser()

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
    fun addCommentInSpot(@PathVariable spotId: String, @RequestBody comment: String) = spotService
            .addCommentInSpot(spotId, comment)

    @GetMapping("/{spotId}/comments")
    fun findComments(@PathVariable spotId: String): List<Comment> = spotService
            .findComments(spotId)

    @PostMapping("/{spotId}/pictures")
    fun addPictures(@PathVariable spotId: String, pictures: Array<MultipartFile>) = spotService.addPictures(spotId, pictures)

    @DeleteMapping("/{spotId}/pictures/{pictureId}")
    fun deletePicture(@PathVariable pictureId: String) = ResponseEntity.noContent()
            .build<Unit>()
            .apply { spotService.deletePicture(pictureId) }

    @PostMapping("/{spotId}/favorites")
    fun addToFavorite(@PathVariable spotId: String) = spotService.addToFavorite(spotId)

    @PutMapping("/{spotId}/upvote")
    fun incrementUpvote(@PathVariable spotId: String) = object {
        val upvote: Long = spotService.incrementUpvote(spotId).upvote
    }


}