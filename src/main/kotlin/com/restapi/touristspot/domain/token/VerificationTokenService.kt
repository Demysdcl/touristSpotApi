package com.restapi.touristspot.domain.token

import com.restapi.touristspot.domain.user.User
import org.springframework.stereotype.Service

@Service
class VerificationTokenService(
        val verificationTokenRepository: VerificationTokenRepository
) {

    fun createVerificationTokenForUser(user: User, token: String) {
        verificationTokenRepository.save(VerificationToken(token = token, user = user))
    }

}