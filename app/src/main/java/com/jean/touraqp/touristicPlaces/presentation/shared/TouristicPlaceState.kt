package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

data class TouristicPlaceState(
    val touristicPlaces: List<TouristicPlace> = emptyList(),
    val selectedTouristicPlace: TouristicPlace? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchQuery: String = "",
    val isLocationPermissionGranted : Boolean? = null,
)
