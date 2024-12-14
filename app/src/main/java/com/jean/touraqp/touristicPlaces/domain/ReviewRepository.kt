package com.jean.touraqp.touristicPlaces.domain

import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.model.Review

interface ReviewRepository {
    suspend fun addReview(review: Review): Review
    suspend fun getByTouristicPlaceId(touristicPLaceId: String): List<Review>
}