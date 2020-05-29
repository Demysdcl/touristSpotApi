package com.restapi.touristspot.domain.picture

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PictureRepository : MongoRepository<Picture, String>