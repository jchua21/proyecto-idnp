package com.jean.touraqp.touristicPlaces.domain.model

import com.jean.touraqp.auth.domain.authentication.model.User

data class Review(
    val id: String,
    val userId: String,
    val touristicPlaceId: String,
    val comment: String,
    val rating: Double,
)

data class ReviewWithUser(
    val id: String,
    val user: User,
    val touristicPlaceId: String,
    val comment: String,
    val rating: Double,
)