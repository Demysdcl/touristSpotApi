package com.restapi.touristspot.domain.category

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun all() = categoryService.all()

    @PostMapping
    fun save(@RequestBody category: String) = categoryService.save(category)

}

