package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.domain.model.Review

sealed class TouristicPlaceEvent{
    data class OnSelectTouristicPlace(val id: String): TouristicPlaceEvent()
    data class OnLocationPermissionResult(val granted: Boolean? = null): TouristicPlaceEvent()
    data class OnAddReviewClick(val review: Review): TouristicPlaceEvent()
}