package com.restapi.touristspot.domain.token

import com.restapi.touristspot.domain.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VerificationTokenRepository : MongoRepository<VerificationToken, String> {

    fun findByToken(token: String): Optional<VerificationToken>

    fun findByUser(user: User): Optional<VerificationToken>
}