package com.restapi.touristspot.domain.category

import com.restapi.touristspot.exception.ObjectAlreadyExistsException
import com.restapi.touristspot.exception.ObjectNotFoundException
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun find(name: String) = categoryRepository.findById(name)
            .orElseThrow { ObjectNotFoundException("Category not found") }

    fun save(category: String): Category = categoryRepository.findById(category)
            .let {
                if (it.isPresent) throw ObjectAlreadyExistsException("Category already exists")
                return@let categoryRepository.save(Category(category))
            }

    fun all(): List<Category> = categoryRepository.findAll()

}