package com.restapi.touristspot.domain.favorite

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("favorites")
class FavoriteController(private val favoriteService: FavoriteService) {

    @GetMapping()
    fun findByLoggedUser() = favoriteService.findByUser()

    @DeleteMapping("/{favoriteId}")
    fun remove(@PathVariable favoriteId: String) = ResponseEntity.noContent()
            .build<Unit>()
            .apply {
                favoriteService.remove(favoriteId)
            }

}