package com.jean.touraqp.touristicPlaces.domain.model

data class TouristicPlace(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val reviews: List<Review> = emptyList()
)