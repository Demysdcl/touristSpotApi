package com.restapi.touristspot.domain.spot

import Spot
import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.picture.Picture
import com.restapi.touristspot.domain.picture.PictureRepository
import com.restapi.touristspot.domain.user.User
import org.bson.BsonBinarySubType
import org.bson.types.Binary
import org.springframework.data.geo.Circle
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class SpotService(private val spotRepository: SpotRepository, private val pictureRepository: PictureRepository) {

    fun findSpotsInFiveKm(longitude: Double, latitude: Double): List<Spot> = spotRepository.findByLocationWithin(
            Circle(Point(longitude, latitude), Distance(5.0, Metrics.KILOMETERS))
    )

    fun findAll() = spotRepository.findAll()

    fun findByName(name: String) = spotRepository.findByName(name)
            .orElseThrow { RuntimeException("Tourist Spot not found") }

    @Transactional
    fun saveAll(spots: List<Spot>) = spotRepository.saveAll(spots)

    @Transactional
    fun deleteAll(spots: List<Spot>) = spotRepository.deleteAll(spots)

    @Transactional
    fun save(picture: MultipartFile, name: String, category: String, longitude: Double, latitude: Double): Spot =
            spotRepository.findByName(name).let {
                if (it.isPresent) throw RuntimeException("Tourist spot already exists")
                return@let spotRepository.save(Spot(
                        name = name,
                        category = Category(category),
                        pictures = listOf(createPicture(picture)),
                        location = arrayOf(longitude, latitude),
                        createBy = User(id = "test", name = "Demys", email = "demysdcl@gmail.com")
                ))
            }


    fun createPicture(file: MultipartFile) = pictureRepository.save(
            Picture(image = Binary(BsonBinarySubType.BINARY, file.bytes)))
    

}