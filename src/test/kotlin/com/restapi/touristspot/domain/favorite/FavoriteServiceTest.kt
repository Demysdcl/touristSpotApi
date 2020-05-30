package com.restapi.touristspot.domain.favorite

import Spot
import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.category.CategoryRepository
import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.domain.user.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class FavoriteServiceTest {

    @Autowired
    lateinit var favoriteService: FavoriteService

    @Autowired
    lateinit var spotRepository: FavoriteRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var favoriteRepository: FavoriteRepository

    lateinit var spot: Spot

    lateinit var favorite: Favorite

    lateinit var user: User

    @BeforeEach
    fun init() {
        user = userRepository.save(User(id = "test", name = "Demys Cota", email = "demysdcl@gmail.com"))
        spot = Spot(
                name = "Park 1",
                category = categoryRepository.save(Category("Park")),
                location = arrayOf(-49.316584, -25.435113),
                createdBy = user
        )
        favorite = favoriteRepository.save(Favorite(spot = spot, favoredBy = user))
    }

    @AfterEach
    fun destroy() {
        userRepository.deleteAll()
        categoryRepository.deleteAll()
        spotRepository.deleteAll()
        favoriteRepository.deleteAll()
    }

    @Test
    fun `should returns favorites from logged user`() {
        val favorites = favoriteService.findByUser()
        assertFalse(favorites.isEmpty())
        assertEquals(1, favorites.size)
    }

    @Test
    fun `given a favorite id then remove`() {
        favorite = favoriteRepository.save(Favorite(spot = spot, favoredBy = user))
        favoriteService.remove(favorite.id!!)
        assertFalse(favoriteRepository.findById(favorite.id!!).isPresent)
    }

    @Test
    fun `given a nonexistent favorite id then throws exception`() {
        val expection = Assertions.assertThrows(RuntimeException::class.java) {
            favoriteService.remove("wrong-id")
        }
        assertEquals("Favorite not found", expection.message)
    }
}