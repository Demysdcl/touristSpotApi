package com.restapi.touristspot.domain.favorite

import com.restapi.touristspot.domain.user.UserService
import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.stereotype.Service

@Service
class FavoriteService(
        private val userService: UserService,
        private val favoriteRepository: FavoriteRepository) {

    fun findByUser() = favoriteRepository.findByFavoredBy(userService.getLoggedUser())

    fun remove(favoriteId: String): Unit = favoriteRepository.findById(favoriteId)
            .map { favoriteRepository.delete(it) }
            .orElseThrow { ObjectNotFoundException("Favorite not found") }

}