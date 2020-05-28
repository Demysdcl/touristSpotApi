package com.restapi.touristspot.domain.category

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Category(
        @Id
        val name: String = ""
)