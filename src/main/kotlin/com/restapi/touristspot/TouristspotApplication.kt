package com.restapi.touristspot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@SpringBootApplication
@EnableMongoRepositories
class TouristspotApplication

fun main(args: Array<String>) {
    runApplication<TouristspotApplication>(*args)
}
