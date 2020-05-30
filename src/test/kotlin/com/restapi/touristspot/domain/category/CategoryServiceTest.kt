package com.restapi.touristspot.domain.category

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class CategoryServiceTest {

    @Autowired
    lateinit var categoryService: CategoryService

    @Test
    fun `given a new category then save it`() {
        val category = categoryService.save("Building")
        Assertions.assertNotNull(category)
        Assertions.assertEquals("Building", category.name)
    }

    @Test
    fun `given a existing category then throws expection`() {
        categoryService.save("Building")
        val expection = Assertions.assertThrows(RuntimeException::class.java) {
            categoryService.save("Building")
        }
        Assertions.assertEquals("Category already exists", expection.message)
    }
}