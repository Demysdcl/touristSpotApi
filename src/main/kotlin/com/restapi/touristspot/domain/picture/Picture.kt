package com.restapi.touristspot.domain.picture

import Spot
import com.restapi.touristspot.domain.user.User
import org.bson.types.Binary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Picture(
        @Id
        val id: String? = null,
        val image: Binary = Binary(ByteArray(0)),
        val from: Spot,
        @DBRef
        val takenBy: User = User()
)

