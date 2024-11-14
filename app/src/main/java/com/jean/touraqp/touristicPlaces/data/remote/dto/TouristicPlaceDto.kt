package com.jean.touraqp.touristicPlaces.data.remote.dto

import com.google.firebase.Timestamp

data class TouristicPlaceDto(
    val name: String = "",
    val description: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val address: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
    val imageUrl: String = "",
    val reviews: List<ReviewDto> = emptyList()
)
