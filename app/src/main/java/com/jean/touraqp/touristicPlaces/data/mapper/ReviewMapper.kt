package com.jean.touraqp.touristicPlaces.data.mapper

import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.domain.model.Review


suspend fun ReviewDto.toReview(): Review {
    return Review(
        id= id!!,
        userId = userId,
        touristicPlaceId = touristicPlaceId,
        comment = comment,
        rating = rating,
    )
}

fun Review.toReviewDTO(): ReviewDto{
    return ReviewDto(
        touristicPlaceId = touristicPlaceId,
        userId = userId,
        comment = comment,
        rating = rating
    )
}
