package com.restapi.touristspot.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
        @Id
        val id: String? = null,
        val name: String = "",
        val email: String = "",
        val password: String = ""
) {
    override fun equals(other: Any?) = other === this ||
            (other is User && id == other.id)

    override fun hashCode() = id?.hashCode() ?: 0

}