package com.restapi.touristspot.domain.spot


import Spot
import org.springframework.data.geo.Circle
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SpotRepository : MongoRepository<Spot, String> {
    fun findByLocationWithin(circle: Circle): List<Spot>
}