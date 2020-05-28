package com.restapi.touristspot.domain.spot

import Spot
import org.springframework.data.geo.Circle
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpotService(private val spotRepository: SpotRepository) {

    fun findSpotsInFiveKm(longitude: Double, latitude: Double): List<Spot> = spotRepository.findByLocationWithin(
            Circle(Point(longitude, latitude), Distance(5.0, Metrics.KILOMETERS))
    )

    fun findByName(name: String) = spotRepository.findByName(name)

    @Transactional
    fun saveAll(spots: List<Spot>) = spotRepository.saveAll(spots)

    @Transactional
    fun deleteAll(spots: List<Spot>) = spotRepository.deleteAll(spots)

    @Transactional
    fun save(spot: Spot) = spotRepository.save(spot)


}