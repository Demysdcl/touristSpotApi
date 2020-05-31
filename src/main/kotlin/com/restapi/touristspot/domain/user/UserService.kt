package com.restapi.touristspot.domain.user

import com.restapi.touristspot.domain.token.VerificationToken
import com.restapi.touristspot.domain.token.VerificationTokenRepository
import com.restapi.touristspot.exception.ObjectAlreadyExistsException
import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val passwordEncoder: PasswordEncoder,
                  private val userRepository: UserRepository,
                  private val verificationTokenRepository: VerificationTokenRepository
) {

    fun findByEmail(email: String): User = userRepository.findByEmail(email)
            .let {
                if (it.isEmpty()) throw ObjectNotFoundException(" Any user not found by e-mail $email")
                it[0]
            }
    

    fun registerUser(user: User): User = userRepository.findByEmail(user.email)
            .let {
                if (it.isNotEmpty()) throw ObjectAlreadyExistsException("The e-mail: ${user.email} already exists")
                userRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
            }

    fun generateNewVerificationToken(email: String, reset: Boolean) =
            findByEmail(email).run {
                verificationTokenRepository.save(findAndUpdateVerificationToken(this))
            }

    private fun findAndUpdateVerificationToken(user: User) = verificationTokenRepository.findByUser(user)
            .run {
                when {
                    isPresent -> get().updateToken(UUID.randomUUID().toString())
                    else -> get()
                }
            }

    fun validateToken(token: String): String = this.verificationTokenRepository
            .findByToken(token)
            .map {
                when {
                    Calendar.getInstance().after(it.expiryDate) -> "expiredToken"
                    else -> return@map ""
                }
            }.orElseGet { "invalidToken" }

    fun getVerificationTokenByToken(token: String): VerificationToken =
            verificationTokenRepository.findByToken(token)
                    .orElseThrow { ObjectNotFoundException("Token not found") }
}
