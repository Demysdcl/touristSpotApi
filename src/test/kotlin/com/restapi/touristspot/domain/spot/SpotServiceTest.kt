package com.restapi.touristspot.domain.spot

import Spot
import com.restapi.touristspot.domain.category.Category
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SpotServiceTest {

    @Autowired
    lateinit var spotService: SpotService
    lateinit var spots: List<Spot>

    @BeforeEach
    fun init() {
        spots = spotService.saveAll(createSpots())
    }

    @AfterEach
    fun destroy() {
        spotService.deleteAll(spots)
    }

    fun createSpots() = arrayListOf(
            Spot(name = "Jardim Botânico", category = Category("Park"), location = arrayOf(-49.2453133, -25.4407956)),
            Spot(name = "Bosque do Alemão", category = Category("Park"), location = arrayOf(-49.287397, -25.405349)),
            Spot(name = "Museu Oscar Niermeyer", category = Category("Museum"), location = arrayOf(-49.267196, -25.410085)),
            Spot(name = "Parque Tangue", category = Category("Park"), location = arrayOf(-49.282461, -25.378846)),
            Spot(name = "Centro histórico de Curitiba", category = Category("Museum"), location = arrayOf(-49.272255, -25.427730))
    )

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
}