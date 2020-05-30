package com.restapi.touristspot.domain.category

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("categories")
class CategoryController(private val categoryService: CategoryService) {

    @PostMapping
    fun save(@RequestBody category: String) = categoryService.save(category)

}

