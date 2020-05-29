package com.restapi.touristspot.domain.spot

import Spot
import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.category.CategoryRepository
import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.domain.user.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import java.util.stream.IntStream


@SpringBootTest
internal class SpotServiceTest {

    @Autowired
    lateinit var spotService: SpotService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    lateinit var spots: List<Spot>

    lateinit var user: User

    lateinit var categories: List<Category>

    @BeforeEach
    fun init() {
        user = userRepository.save(User(name = "Demys Cota", email = "demysdcl@gmail.com"))
        categories = categoryRepository.saveAll(createCategories())
        spots = spotService.saveAll(createSpots())

    }

    @AfterEach
    fun destroy() {
        spotService.deleteAll(spots)
        userRepository.deleteAll()
    }

    fun createCategories() = arrayListOf(
            Category("Park"),
            Category("Museum"),
            Category("Monument")
    )

    fun createSpots() = arrayListOf(
            Spot(name = "Park 1", category = categories[0], location = arrayOf(-49.316584, -25.435113), createBy = user),
            Spot(name = "Park 2", category = categories[0], location = arrayOf(-49.316585, -25.435114), createBy = user),
            Spot(name = "Park 3", category = categories[0], location = arrayOf(-49.316586, -25.435115), createBy = user),
            Spot(name = "Jardim Botânico", category = categories[0], location = arrayOf(-49.2453133, -25.4407956), createBy = user),
            Spot(name = "Bosque do Alemão", category = categories[0], location = arrayOf(-49.287397, -25.405349), createBy = user),
            Spot(name = "Museu Oscar Niermeyer", category = categories[1], location = arrayOf(-49.267196, -25.410085), createBy = user),
            Spot(name = "Parque Tanguá", category = categories[0], location = arrayOf(-49.282461, -25.378846), createBy = user),
            Spot(name = "Centro histórico de Curitiba", category = categories[1], location = arrayOf(-49.272255, -25.427730), createBy = user)
    )


    @Test
    fun `given latitude and longitude near 3 park return the 3 parks`() {
        val spotInFiveKm = spotService.findSpotsInFiveKm(-49.316580, -25.435110)
        assertFalse(spotInFiveKm.isEmpty())
        assertEquals(3, spotInFiveKm.size)
        IntStream.range(0, spotInFiveKm.size)
                .forEach {
                    assertEquals("Park ${it + 1}", spotInFiveKm[it].name)
                }
    }

    @Test
    fun `given a point near Jardim Botânico then return spot Jardim Botânico`() {
        val spotInFiveKm = spotService.findSpotsInFiveKm(-49.2453130, -25.4407926)
        assertFalse(spotInFiveKm.isEmpty())
        assertEquals(1, spotInFiveKm.size)
        assertEquals("Jardim Botânico", spotInFiveKm[0].name)
    }

    @Test
    fun `given a point near Bosque do Alemão then return spot Bosque do Alemão`() {
        val spotInFiveKm = spotService.findSpotsInFiveKm(-49.287300, -25.405300)
        assertFalse(spotInFiveKm.isEmpty())
        assertEquals(1, spotInFiveKm.size)
        assertEquals("Bosque do Alemão", spotInFiveKm[0].name)
    }

    @Test
    fun `given a point with no nearby tourist spot then return empty list`() {
        val spotInFiveKm = spotService.findSpotsInFiveKm(-49.273289, -25.467319)
        assert(spotInFiveKm.isEmpty())
    }

    @Test
    fun `given name Jardim Botânico return then return Spot Jardim Botânico`() {
        val foundByName = spotService.findByName("Jardim Botânico")
        assertNotNull(foundByName)
        assertEquals("Jardim Botânico", foundByName.name)
    }

    @Test
    fun `given nonexistent name in Spot Collecion return then return Empty Optional`() {
        val expection = assertThrows(RuntimeException::class.java) {
            spotService.findByName("Cristo Redentor")
        }
        assertEquals("Tourist Spot not found", expection.message)
    }

    @Test
    fun `save Spot tests`() {
        `given a params and a File then save new Spot`()
        `given a name already exists thorws expecption`()
    }

    fun `given a params and a File then save new Spot`() {
        val newSpot = createSpot()
        assertNotNull(newSpot)
        assertEquals("Stonehenge", newSpot.name)
    }


    fun `given a name already exists thorws expecption`() {
        val expectation = assertThrows(RuntimeException::class.java) {
            createSpot()
        }
        assertEquals("Tourist spot already exists", expectation.message)
    }

    fun createSpot() = spotService.save(
            MockMultipartFile("picture.png", ByteArray(Int.SIZE_BYTES)),
            "Stonehenge",
            "Monument",
            -1.826502,
            51.183236)

    @Test
    fun `given a comment do Spot then save comment in Spot`() {
        val comment = spotService.addCommentInSpot(spots[0].id!!, "Cool park")
        assertEquals("Cool park", comment.description)
    }

    @Test
    fun `given an nonexistent id from Spot to save comment then thorws expecption`() {
        val expectation = assertThrows(RuntimeException::class.java) {
            val comment = spotService.addCommentInSpot("wrong id", "Cool park")
        }
        assertEquals("Tourist Spot not found", expectation.message)
    }

    @Test
    fun `given an spot id then return its comments`() {
        val spotID = spots[0].id!!
        spotService.addCommentInSpot(spotID, "Cool park")
        spotService.addCommentInSpot(spotID, "The First park")
        val commments = spotService.findComments(spotID)
        assertFalse(commments.isEmpty())
        assertEquals(2, commments.size)
    }

    @Test
    fun `given an nonexistent id from Spot to get comments then thorws expecption`() {
        val expectation = assertThrows(RuntimeException::class.java) {
            spotService.findComments("wrong id")
        }
        assertEquals("Tourist Spot not found", expectation.message)
    }


}