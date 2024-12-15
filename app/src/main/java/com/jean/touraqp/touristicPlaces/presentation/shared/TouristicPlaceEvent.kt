package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser

sealed class TouristicPlaceEvent{
    data class OnSearchInputChanged(val query: String): TouristicPlaceEvent()
    object OnSearchAction: TouristicPlaceEvent()
    data class OnSelectTouristicPlace(val id: String): TouristicPlaceEvent()
    data class OnLocationPermissionResult(val granted: Boolean? = null): TouristicPlaceEvent()
    data class OnReviewAdded(val review: ReviewWithUser): TouristicPlaceEvent()
}