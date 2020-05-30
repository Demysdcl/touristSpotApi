package com.restapi.touristspot.domain.category

import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun find(name: String) = categoryRepository.findById(name)
            .orElse(categoryRepository.save(Category(name)))

    fun save(category: String): Category = categoryRepository.findById(category)
            .let {
                if (it.isPresent) throw RuntimeException("Category already exists")
                return@let categoryRepository.save(Category(category))
            }

}