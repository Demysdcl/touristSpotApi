package com.restapi.touristspot.domain.spot


import Spot
import com.restapi.touristspot.domain.user.User
import org.springframework.data.geo.Circle
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SpotRepository : MongoRepository<Spot, String> {

    fun findByLocationWithin(circle: Circle): List<Spot>

    fun findByName(name: String): Optional<Spot>

    fun findByCreatedBy(user: User): List<Spot>

}