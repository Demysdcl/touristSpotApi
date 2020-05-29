package com.restapi.touristspot.domain.category

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "category")
data class Category(
        @Id
        val name: String = ""
)