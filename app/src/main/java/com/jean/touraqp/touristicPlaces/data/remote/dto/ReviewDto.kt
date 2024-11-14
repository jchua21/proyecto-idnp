package com.jean.touraqp.touristicPlaces.data.remote.dto

data class ReviewDto(
    val comment: String ="",
    val rate: Double = 0.0,
    val username: String = ""
)