package com.restapi.touristspot.util

import java.util.*

fun createExpirationDate(): Date = Calendar.getInstance()
        .apply { add(Calendar.MINUTE, 60 * 24) }
        .time