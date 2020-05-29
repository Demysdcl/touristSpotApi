package com.restapi.touristspot.domain.comment

import Spot
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : MongoRepository<Comment, String> {

    fun findByAboutOf(spot: Spot): List<Comment>

}