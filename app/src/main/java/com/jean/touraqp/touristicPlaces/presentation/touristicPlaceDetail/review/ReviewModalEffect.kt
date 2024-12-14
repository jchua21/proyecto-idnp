package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser

sealed class ReviewModalEffect{
    data class OnReviewAdded(val review: ReviewWithUser): ReviewModalEffect()
}