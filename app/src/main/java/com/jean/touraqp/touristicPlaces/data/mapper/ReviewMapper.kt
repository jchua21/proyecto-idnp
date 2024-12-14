package com.jean.touraqp.touristicPlaces.data.mapper

import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewInsertDto
import com.jean.touraqp.touristicPlaces.domain.model.Review


fun ReviewDto.toReview(): Review {
    return Review(
        id= id!!,
        userId = userId,
        touristicPlaceId = touristicPlaceId,
        comment = comment,
        rating = rating,
    )
}

fun Review.toReviewInsertDTO(): ReviewInsertDto{
    return ReviewInsertDto(
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
        rating = rating,
    )
}
