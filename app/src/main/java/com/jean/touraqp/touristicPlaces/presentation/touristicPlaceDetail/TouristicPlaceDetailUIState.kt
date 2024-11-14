package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

data class TouristicPlaceDetailUIState(
    val touristicPlace: TouristicPlace? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
)