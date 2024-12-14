package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.presentation.model.TouristicPlaceWithReviewsUI

data class TouristicPlaceState(
    val touristicPlaces: List<TouristicPlaceWithReviewsUI> = emptyList(),
    val selectedTouristicPlace: TouristicPlaceWithReviewsUI? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchQuery: String = "",
    val isLocationPermissionGranted : Boolean? = null,
)
