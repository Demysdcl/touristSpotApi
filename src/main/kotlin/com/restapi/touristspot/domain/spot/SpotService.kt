package com.restapi.touristspot.domain.spot

import Spot
import com.restapi.touristspot.domain.category.CategoryService
import com.restapi.touristspot.domain.comment.Comment
import com.restapi.touristspot.domain.comment.CommentRepository
import com.restapi.touristspot.domain.picture.Picture
import com.restapi.touristspot.domain.picture.PictureRepository
import com.restapi.touristspot.domain.user.User
import com.restapi.touristspot.domain.user.UserRepository
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
class SpotService(private val spotRepository: SpotRepository,
                  private val categoryService: CategoryService,
                  private val commentRepository: CommentRepository,
                  private val userRepository: UserRepository,
                  private val pictureRepository: PictureRepository) {

    fun findSpotsInFiveKm(longitude: Double, latitude: Double): List<Spot> = spotRepository.findByLocationWithin(
            Circle(Point(longitude, latitude), Distance(5.0, Metrics.KILOMETERS))
    )

    fun findAll(): List<Spot> = spotRepository.findAll()

    fun findByName(name: String): Spot = spotRepository.findByName(name)
            .orElseThrow { RuntimeException("Tourist Spot not found") }

    @Transactional
    fun saveAll(spots: List<Spot>): List<Spot> = spotRepository.saveAll(spots)

    @Transactional
    fun deleteAll(spots: List<Spot>) = spotRepository.deleteAll(spots)

    @Transactional
    fun save(picture: MultipartFile, name: String, category: String, longitude: Double, latitude: Double): Spot =
            spotRepository.findByName(name).let {
                if (it.isPresent) throw RuntimeException("Tourist spot already exists")
                val spot = spotRepository.save(Spot(
                        name = name,
                        category = categoryService.find(category),
                        location = arrayOf(longitude, latitude),
                        createBy = temporaryUser()
                ))
                createPicture(picture, spot)
                return@let spot
            }


    fun createPicture(file: MultipartFile, spot: Spot) = pictureRepository.save(
            Picture(takenBy = temporaryUser(), image = Binary(BsonBinarySubType.BINARY, file.bytes), from = spot))

    @Transactional
    fun addCommentInSpot(id: String, comment: String): Comment = spotRepository.findById(id)
            .map {
                commentRepository.save(Comment(description = comment, aboutOf = it, commentedBy = temporaryUser()))
            }.orElseThrow { RuntimeException("Tourist Spot not found") }

    fun findComments(spotId: String): List<Comment> = spotRepository.findById(spotId)
            .map { commentRepository.findByAboutOf(it) }
            .orElseThrow { RuntimeException("Tourist Spot not found") }

    fun temporaryUser(): User = userRepository.findById("test")
            .orElse(userRepository.save(User(id = "test", name = "Demys", email = "demysdcl@gmail.com")))
}