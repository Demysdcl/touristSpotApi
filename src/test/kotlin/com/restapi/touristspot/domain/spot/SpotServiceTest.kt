package com.restapi.touristspot.domain.spot

import Spot
import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.category.CategoryRepository
import com.restapi.touristspot.domain.picture.PictureRepository
import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.domain.user.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import java.util.stream.IntStream


@SpringBootTest
@ActiveProfiles("test")
internal class SpotServiceTest {


    @Autowired
    lateinit var spotService: SpotService

    @Autowired
    lateinit var spotRepository: SpotRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var pictureRepository: PictureRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    lateinit var spots: List<Spot>

    lateinit var user: User

    lateinit var categories: List<Category>


    @BeforeEach
    fun init() {
        user = userRepository.save(User(id = "test", name = "Demys Cota", email = "demysdcl@gmail.com"))
        categories = categoryRepository.saveAll(createCategories())
        spots = spotService.saveAll(createSpots())
    }

    @AfterEach
    fun destroy() {
        spotRepository.deleteAll()
        userRepository.deleteAll()
        pictureRepository.deleteAll()
    }

    fun createCategories() = arrayListOf(
            Category("Park"),
            Category("Museum"),
            Category("Monument")
    )

    fun createSpots() = arrayListOf(
            Spot(name = "Park 1", category = categories[0], location = arrayOf(-49.316584, -25.435113), createdBy = user),
            Spot(name = "Park 2", category = categories[0], location = arrayOf(-49.316585, -25.435114), createdBy = user),
            Spot(name = "Park 3", category = categories[0], location = arrayOf(-49.316586, -25.435115), createdBy = user),
            Spot(name = "Jardim Botânico", category = categories[0], location = arrayOf(-49.2453133, -25.4407956), createdBy = user),
            Spot(name = "Bosque do Alemão", category = categories[0], location = arrayOf(-49.287397, -25.405349), createdBy = user),
            Spot(name = "Museu Oscar Niermeyer", category = categories[1], location = arrayOf(-49.267196, -25.410085), createdBy = user),
            Spot(name = "Parque Tanguá", category = categories[0], location = arrayOf(-49.282461, -25.378846), createdBy = user),
            Spot(name = "Centro histórico de Curitiba", category = categories[1], location = arrayOf(-49.272255, -25.427730), createdBy = user)
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
    fun `given nonexistent name in Spot Collecion then return Empty Optional`() {
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
        val exception = assertThrows(RuntimeException::class.java) {
            createSpot()
        }
        assertEquals("Tourist spot already exists", exception.message)
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
    fun `given a nonexistent spot id  to save comment then thorws expecption`() {
        val exception = assertThrows(RuntimeException::class.java) {
            spotService.addCommentInSpot("wrong-id", "Cool park")
        }
        assertEquals("Tourist Spot not found", exception.message)
    }

    @Test
    fun `given a spot id then return its comments`() {
        val spotID = spots[0].id!!
        spotService.addCommentInSpot(spotID, "Cool park")
        spotService.addCommentInSpot(spotID, "The First park")
        val comments = spotService.findComments(spotID)
        assertFalse(comments.isEmpty())
        assertEquals(2, comments.size)
    }

    @Test
    fun `given an nonexistent spot id to get comments then thorws expecption`() {
        val exception = assertThrows(RuntimeException::class.java) {
            spotService.findComments("wrong-id")
        }
        assertEquals("Tourist Spot not found", exception.message)
    }

    @Test
    fun `given a pictures and a spot id then save the picture`() {
        val picture = spotService.addPictures(
                spots[0].id!!,
                arrayOf(MockMultipartFile("picture.png", ByteArray(0)))
        )
        assertNotNull(picture)
    }

    @Test
    fun `given given a nonexistent id Spot to save pictures then thorws expecption`() {
        val exception = assertThrows(RuntimeException::class.java) {
            spotService.addPictures("wrong-id",
                    arrayOf(MockMultipartFile("picture.png", ByteArray(0)))
            )
        }
        assertEquals("Tourist Spot not found", exception.message)
    }

    @Test
    fun `given a spot id and picture id then remove picture`() {
        val pictures = spotService.addPictures(spots[0].id!!,
                arrayOf(MockMultipartFile("picture.png", ByteArray(0)))
        )
        assert(pictureRepository.findById(pictures[0].id!!).isPresent)
        spotService.deletePicture(pictures[0].id!!)
        assertFalse(pictureRepository.findById(pictures[0].id!!).isPresent)
    }

    @Test
    fun `given a spot to favorite then save the favorite`() {
        val favorite = spotService.addToFavorite(spots[0].id!!)
        assertNotNull(favorite)
        assertEquals(spots[0].id!!, favorite.spot.id)
    }

    @Test
    fun `given given a nonexistent id Spot to add in favorites then thorws expecption`() {
        val exception = assertThrows(RuntimeException::class.java) {
            spotService.addToFavorite("wrong-id")
        }
        assertEquals("Tourist Spot not found", exception.message)
    }

    @Test
    fun `given a spot id to upvote update upvote from spot`() {
        var spot = spotService.incrementUpvote(spots[0].id!!)
        assertEquals(1, spot.upvote)

        spot = spotService.incrementUpvote(spots[0].id!!)
        assertEquals(2, spot.upvote)
    }

    @Test
    fun `given given a nonexistent id Spot to add increment upvote then thorws expecption`() {
        val exception = assertThrows(RuntimeException::class.java) {
            spotService.incrementUpvote("wrong-id")
        }
        assertEquals("Tourist Spot not found", exception.message)
    }

    @Test
    fun `should returns the spots created by logged user`() {
        val spots = spotService.findByLoggedUser()
        assertFalse(spots.isEmpty())
        assertEquals(8, spots.size)
    }

}