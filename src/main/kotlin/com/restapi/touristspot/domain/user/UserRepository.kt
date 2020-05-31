package com.restapi.touristspot.domain.user

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): List<User>

    fun findByName(name: String): Optional<User>
}