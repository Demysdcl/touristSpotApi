package com.restapi.touristspot.exception

class StandardErrorResponse(
        val timestamp: Long,
        val status: Int,
        val error: String,
        val message: String,
        val path: String
)