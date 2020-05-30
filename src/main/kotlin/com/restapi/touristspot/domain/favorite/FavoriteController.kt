package com.restapi.touristspot.domain.favorite

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("favorites")
class FavoriteController(private val favoriteService: FavoriteService) {

    @GetMapping()
    fun findByLoggedUser() = favoriteService.findByUser()

    @DeleteMapping("/{favoriteId}")
    fun remove(@PathVariable favoriteId: String) = favoriteService.remove(favoriteId)
}