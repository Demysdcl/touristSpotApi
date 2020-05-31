package com.restapi.touristspot.domain.category

import com.restapi.touristspot.exception.ObjectAlreadyExistsException
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun find(name: String) = categoryRepository.findById(name)
            .orElse(categoryRepository.save(Category(name)))

    fun save(category: String): Category = categoryRepository.findById(category)
            .let {
                if (it.isPresent) throw ObjectAlreadyExistsException("Category already exists")
                return@let categoryRepository.save(Category(category))
            }

    fun all(): List<Category> = categoryRepository.findAll()
            .let {
                when {
                    it.isEmpty() -> categoryRepository.saveAll(listOf(
                            Category("Park"),
                            Category("Museum"),
                            Category("Theater"),
                            Category("Monument")
                    ))
                    else -> it
                }
            }

}