package com.restapi.touristspot.config

import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.category.CategoryRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class RunnerConfig(private val categoryRepository: CategoryRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val categories = categoryRepository.findAll()
        if (categories.isEmpty())
            categoryRepository.saveAll(listOf(
                    Category("Park"),
                    Category("Museum"),
                    Category("Theater"),
                    Category("Monument")
            ))
    }
    

}