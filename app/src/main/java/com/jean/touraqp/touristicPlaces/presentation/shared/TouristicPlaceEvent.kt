package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser
import com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review.ReviewUI

sealed class TouristicPlaceEvent{
    data class OnSelectTouristicPlace(val id: String): TouristicPlaceEvent()
    data class OnLocationPermissionResult(val granted: Boolean? = null): TouristicPlaceEvent()
    data class OnReviewAdded(val review: ReviewWithUser): TouristicPlaceEvent()
}