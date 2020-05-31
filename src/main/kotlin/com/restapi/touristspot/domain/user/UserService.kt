package com.restapi.touristspot.domain.user

import com.restapi.touristspot.exception.ObjectAlreadyExistsException
import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val passwordEncoder: PasswordEncoder,
                  private val userRepository: UserRepository
) {

    fun findByEmail(email: String): User = userRepository.findByEmail(email)
            .orElseThrow { ObjectNotFoundException(" Any user not found by e-mail $email") }


    fun registerUser(user: User): User = userRepository.findByEmail(user.email)
            .let {
                if (it.isPresent) throw ObjectAlreadyExistsException("The e-mail: ${user.email} already exists")
                userRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
            }


    fun getLoggedUser() = findByEmail(getLeggedUserEmail())

    fun getLeggedUserEmail(): String = SecurityContextHolder.getContext().authentication.principal
            .let {
                when (it) {
                    is UserDetails -> it.username
                    else -> it.toString()
                }
            }


}
