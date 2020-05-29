package com.restapi.touristspot.domain.category

import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun find(name: String) = categoryRepository.findById(name)
            .orElse(categoryRepository.save(Category(name)))

}