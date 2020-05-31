package com.restapi.touristspot.domain.token

import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.util.createExpirationDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class VerificationToken(
        @Id
        val id: String? = null,
        val token: String = "",
        @DBRef
        val user: User = User(),
        val expiryDate: Date = createExpirationDate()
) {
    fun updateToken(token: String) = this.copy(
            token = token, expiryDate = createExpirationDate()
    )
}