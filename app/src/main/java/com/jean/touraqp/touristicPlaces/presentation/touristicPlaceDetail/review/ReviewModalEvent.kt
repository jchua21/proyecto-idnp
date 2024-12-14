package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

sealed class ReviewModalEvent {
    data class OnUserCommented(val comment: String) : ReviewModalEvent()
    data class OnUserSelectedRating(val rating: Double) : ReviewModalEvent()
    data class OnUserAddReview(val userId: String, val touristicPlaceId: String) : ReviewModalEvent()
}