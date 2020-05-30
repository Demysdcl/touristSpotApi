package com.restapi.touristspot.domain.user

import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository) {
    
    fun findByEmail(email: String) = userRepository.findByEmail(email)
            .orElseThrow { ObjectNotFoundException("User not foun by e-mail $email") }
}
