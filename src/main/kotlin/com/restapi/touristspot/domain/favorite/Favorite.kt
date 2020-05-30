package com.restapi.touristspot.domain.favorite

import Spot
import com.restapi.touristspot.domain.user.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Favorite(
        @Id
        val id: String? = null,
        val spot: Spot = Spot(),
        val favoredBy: User = User()
)