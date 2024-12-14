package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import com.jean.touraqp.touristicPlaces.domain.model.Review

data class ReviewUI(
    val id: String= "",
    val userId: String = "",
    val touristicPlaceId: String = "",
    val comment: String = "",
    val rating: Double = 0.0,
)

// Mapper functions
fun ReviewUI.toReview(): Review{
    return Review(
        id= id,
        userId = userId,
        touristicPlaceId = touristicPlaceId,
        comment = comment,
        rating = rating,
    )
}