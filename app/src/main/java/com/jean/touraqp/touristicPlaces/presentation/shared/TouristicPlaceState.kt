package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

data class TouristicPlaceState(
    val touristicPlaces: List<TouristicPlace> = emptyList(),
    val selectedTouristicPlace: TouristicPlace? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchQuery: String = ""
)
