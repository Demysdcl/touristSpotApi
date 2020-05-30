package com.restapi.touristspot.domain.favorite

import com.restapi.touristspot.domain.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : MongoRepository<Favorite, String> {
    fun findByFavoredBy(user: User): List<Favorite>
}