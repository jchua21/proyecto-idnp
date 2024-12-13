package com.jean.touraqp.touristicPlaces.domain.model

import com.jean.touraqp.auth.domain.authentication.model.User

data class Review(
    val user: User,
    val comment: String,
    val rate: Double,
)