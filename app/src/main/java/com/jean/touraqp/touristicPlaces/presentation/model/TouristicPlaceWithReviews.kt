package com.jean.touraqp.touristicPlaces.presentation.model

import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser

data class TouristicPlaceWithReviewsUI(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val reviews: MutableList<ReviewWithUser>
)