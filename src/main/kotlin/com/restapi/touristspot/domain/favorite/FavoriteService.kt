package com.restapi.touristspot.domain.favorite

import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.domain.user.UserRepository
import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.stereotype.Service

@Service
class FavoriteService(
        private val userRepository: UserRepository,
        private val favoriteRepository: FavoriteRepository) {

    fun findByUser() = favoriteRepository.findByFavoredBy(temporaryUser())

    fun temporaryUser(): User = userRepository.findById("test")
            .orElse(userRepository.save(User(id = "test", name = "Demys", email = "demysdcl@gmail.com")))

    fun remove(favoriteId: String): Unit = favoriteRepository.findById(favoriteId)
            .map { favoriteRepository.delete(it) }
            .orElseThrow { ObjectNotFoundException("Favorite not found") }

}