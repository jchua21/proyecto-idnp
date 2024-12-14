package com.jean.touraqp.touristicPlaces.data.repository.review

import com.jean.touraqp.touristicPlaces.data.repository.review.datasource.ReviewRemoteDatasource
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import javax.inject.Inject

class ReviewRepositoryImp @Inject constructor(
    private val remoteDatasource: ReviewRemoteDatasource
):ReviewRepository{
    override suspend fun addReview(review: Review): Review{
       return remoteDatasource.addReview(review)
    }

    override suspend fun getByTouristicPlaceId(touristicPLaceId: String): List<Review> {
        return remoteDatasource.getByTouristicPlaceId(touristicPLaceId)
    }

}