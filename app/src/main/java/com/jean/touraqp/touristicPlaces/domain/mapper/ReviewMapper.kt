package com.jean.touraqp.touristicPlaces.domain.mapper

import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewCreationDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.domain.model.Review

fun Review.toReviewCreationDto(): ReviewCreationDto {
    return ReviewCreationDto(
        userId = user.id!!,
        comment = comment,
        rate = rate,
    )
}