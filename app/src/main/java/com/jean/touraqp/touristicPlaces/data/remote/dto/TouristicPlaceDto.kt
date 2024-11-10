package com.jean.touraqp.touristicPlaces.data.remote.dto

import com.google.firebase.Timestamp

data class TouristicPlaceDto(
    val name: String = "",
    val description: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val address: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
    val imageUrl: String = ""
)
