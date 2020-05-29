package com.restapi.touristspot.domain.comment

import Spot
import com.restapi.touristspot.domain.user.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Comment(
        @Id
        val id: String? = null,
        val description: String = "",
        @DBRef
        val aboutOf: Spot,
        @DBRef
        val commentedBy: User = User()

)